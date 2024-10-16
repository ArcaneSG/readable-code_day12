package cleancode.studycafe.tobe.model.order;

import cleancode.studycafe.tobe.io.provider.LockerPassFileReader;
import cleancode.studycafe.tobe.io.provider.SeatPassFileReader;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobe.provider.LockerPassProvider;
import cleancode.studycafe.tobe.provider.SeatPassProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafePassOrderTest {

    private final SeatPassProvider seatPassProvider = new SeatPassFileReader();
    private final LockerPassProvider lockerPassProvider = new LockerPassFileReader();


    @DisplayName("좌석 이용권과 락커 이용권을 선택했을때 주문을 생성한다.")
    @Test
    void createOrder() {
        // given
        StudyCafeSeatPass studyCafeSeatPass = seatPassProvider.getSeatPasses()
                                                    .findPassBy(StudyCafePassType.FIXED).get(0);
        Optional<StudyCafeLockerPass> optionalLockerPass = lockerPassProvider.getLockerPasses()
            .findLockerPassBy(studyCafeSeatPass);

        // when
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(
            studyCafeSeatPass,
            optionalLockerPass.orElse(null)
        );

        // then
        assertThat(passOrder.getSeatPass()).isEqualTo(studyCafeSeatPass);
        assertThat(passOrder.getLockerPass()).isEqualTo(optionalLockerPass);
    }


    @DisplayName("좌석 이용권 + 락커 이용권 금액을 더하면 주문에서의 총 금액과 동일하다.")
    @Test
    void createOrderPrice() {
        // given
        StudyCafeSeatPass studyCafeSeatPass = seatPassProvider.getSeatPasses()
            .findPassBy(StudyCafePassType.HOURLY).get(0);
        Optional<StudyCafeLockerPass> optionalLockerPass = lockerPassProvider.getLockerPasses()
            .findLockerPassBy(studyCafeSeatPass);

        // when
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(
            studyCafeSeatPass,
            optionalLockerPass.orElse(null)
        );

        // then
        int seatPassPrice = passOrder.getSeatPass().getPrice() - passOrder.getSeatPass().getDiscountPrice();
        int lockerPassPrice = passOrder.getLockerPass().isPresent() ? passOrder.getLockerPass().get().getPrice() : 0;

        assertThat(passOrder.getTotalPrice()).isEqualTo(seatPassPrice + lockerPassPrice);
    }
}
