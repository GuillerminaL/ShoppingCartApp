package ar.shoppingcart.cart.hb.application.purchase.implementation;

import ar.shoppingcart.cart.hb.application.cart.CheckCartExistenceService;
import ar.shoppingcart.cart.hb.application.cart.implementation.SetProcessCartServiceImpl;
import ar.shoppingcart.cart.hb.application.purchase.PostCartPurchaseService;
import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.exception.EmptyCartException;
import ar.shoppingcart.cart.hb.application.cart.exception.UnavailableCartException;
import ar.shoppingcart.cart.hb.application.cartProduct.GetAllCartProductsService;
import ar.shoppingcart.cart.hb.application.cartProduct.GetAvailableCartProductsPartialAmountService;
import ar.shoppingcart.cart.hb.application.product.GetProductService;
import ar.shoppingcart.cart.hb.application.product.GetProductsWithUnavailableStockService;
import ar.shoppingcart.cart.hb.application.product.UpdateProductStockService;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.UnavailableProductsException;
import ar.shoppingcart.cart.hb.application.purchase.port.PurchasePort;
import ar.shoppingcart.cart.hb.application.purchase.purchaseItem.port.PurchaseItemPort;
import ar.shoppingcart.cart.hb.application.user.CheckActiveUserExistenceService;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import ar.lamansys.cart.hc.domain.*;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import ar.shoppingcart.cart.hc.domain.ProductBo;
import ar.shoppingcart.cart.hc.domain.PurchaseBo;
import ar.shoppingcart.cart.hc.domain.PurchaseItemBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostCartPurchaseServiceImpl implements PostCartPurchaseService {
    PurchasePort purchasePort;
    PurchaseItemPort purchaseItemPort;

    CheckActiveUserExistenceService checkActiveUserExistenceService;
    CheckCartExistenceService checkCartExistenceService;
    SetProcessCartServiceImpl setProcessCartService;
    GetProductsWithUnavailableStockService getProductsWithUnavailableStockService;
    GetAllCartProductsService getAllCartProductsService;
    GetAvailableCartProductsPartialAmountService getAvailableCartProductsPartialAmountService;
    GetProductService getProductService;
    UpdateProductStockService updateProductStockService;

    public PurchaseBo run(Integer cartId, String userId) throws UserNotFoundException, CartNotFoundException,
            UnavailableCartException, UnavailableProductsException, EmptyCartException {

        checkActiveUserExistenceService.run( userId );

        checkCartExistenceService.run( cartId, userId );

        List<Integer> unavailableProducts = getProductsWithUnavailableStockService.run( cartId, userId );
        if ( ! unavailableProducts.isEmpty() ) {
            throw new UnavailableProductsException( unavailableProducts );
        }

        List<CartProductBo> cartProductBos = getAllCartProductsService.run( cartId, userId );
        if ( cartProductBos.isEmpty() ) {
            throw new EmptyCartException( cartId, userId );
        }

        //If cart is processed will throw UnavailableCartException...
        setProcessCartService.run( cartId, userId, true);

        PurchaseBo purchaseBo = new PurchaseBo( cartId, userId );
        purchaseBo = purchasePort.save( purchaseBo );
        Integer purchaseId = purchaseBo.getId();

        List<PurchaseItemBo> purchaseItems = cartProductBos
                .stream()
                .map(
                        cartProductBo -> {
                            PurchaseItemBo newPurchaseItem = new PurchaseItemBo();
                            try {
                                ProductBo productBo = getProductService.getProduct( cartProductBo.getProductId() );
                                Integer currentStock = productBo.getStock();
                                Integer newStock = currentStock - cartProductBo.getQuantity();
                                if ( newStock >= 0 ) {
                                    updateProductStockService.run( cartProductBo.getProductId(), newStock );
                                }

                                Double purchaseItemAmount = productBo.getPrice()*cartProductBo.getQuantity();
                                newPurchaseItem = new PurchaseItemBo(purchaseId, productBo.getId(),
                                        productBo.getDescription(), newStock, productBo.getPrice(),
                                        cartProductBo.getQuantity(), purchaseItemAmount);

                                newPurchaseItem = purchaseItemPort.save( newPurchaseItem );

                            } catch (ProductNotFoundException e) {
                                System.out.println("Couldn´t process purchase item: " + cartProductBo + "Product not found");
                            }
                            return newPurchaseItem;
                        }
                )
                .collect(Collectors.toList());

        purchaseBo.setAmount(
                purchaseItemPort.getSumOfPurchaseItemsAmount( purchaseBo.getId() )
        );
        purchasePort.save( purchaseBo );

        purchaseBo.setPurchaseItems( purchaseItems );

        return purchaseBo;
    }
}
