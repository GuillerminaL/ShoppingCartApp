package ar.shoppingcart.cart.hb.application.user.implementation;

import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import ar.shoppingcart.cart.hb.application.user.port.UserPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

class CheckUserExistenceServiceImplTest {
    UserPort userPort = mock(UserPort.class);
    CheckActiveUserExistenceServiceImpl checkUserExistenceService = new CheckActiveUserExistenceServiceImpl(userPort);

    @Test
    void givenANotExistingUser_run_doesNothingAndThrowsException() {
        //Arrange
        String userId = "userId";

        when( userPort.exists(userId) )
                .thenReturn(false);

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkUserExistenceService.run( userId )
        );

        //Assert
        verify( userPort, times( 1 ) ).exists( userId );
        verify( userPort, times( 0 ) ).isActive( userId );

        Assertions.assertThat(throwable)
                .as("Checks not existing user")
                .isNotNull()
                .isInstanceOf(UserNotFoundException.class)
                .extracting("username")
                .isEqualTo(userId);
    }

    @Test
    void givenAnExistingAndInactiveUser_run_throwsUserNotFoundException() {
        //Arrange
        String userId = "userId";

        when( userPort.exists(userId) )
                .thenReturn(true);

        when( userPort.isActive(userId) )
                .thenReturn(false);

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkUserExistenceService.run( userId )
        );

        //Assert
        verify( userPort, times( 1 ) ).exists( userId );
        verify( userPort, times( 1 ) ).isActive( userId );

        Assertions.assertThat(throwable)
                .as("Checks not existing user")
                .isNotNull()
                .isInstanceOf(UserNotFoundException.class)
                .extracting("username")
                .isEqualTo(userId);
    }

    @Test
    void givenAnExistingAndActiveUser_run_doesNothingAndDoesNotThrowsException() {
        //Arrange
        String userId = "userId";

        when( userPort.exists(userId) )
                .thenReturn(true);

        when( userPort.isActive(userId) )
                .thenReturn(true);

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkUserExistenceService.run( userId )
        );

        //Assert
        verify( userPort, times( 1 ) ).exists( userId );
        verify( userPort, times( 1 ) ).isActive( userId );

        Assertions.assertThat(throwable)
                .as("Checks existing user")
                .isNull();
    }
}