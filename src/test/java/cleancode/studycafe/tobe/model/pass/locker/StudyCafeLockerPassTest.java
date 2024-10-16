package cleancode.studycafe.tobe.model.pass.locker;

import cleancode.studycafe.tobe.io.provider.LockerPassFileReader;
import cleancode.studycafe.tobe.io.provider.SeatPassFileReader;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPasses;
import cleancode.studycafe.tobe.provider.LockerPassProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeLockerPassTest {

    @DisplayName("CSV 파일정보를 통해 락커 이용권 리스트를 생성한다")
    @Test
    void getSeatPasses() {
        // given
        LockerPassProvider lockerPassProvider = new LockerPassFileReader();

        // when
        StudyCafeLockerPasses lockerPasses = lockerPassProvider.getLockerPasses();

        // then
        assertThat(lockerPasses).isNotNull();
    }

    @DisplayName("시간 단위 이용권을 선택 했을 때 락커 이용권은 없다.")
    @Test
    void getHourlySeatPassesB() {
        // given
        SeatPassFileReader seatPassFileReader = new SeatPassFileReader();
        StudyCafeSeatPasses seatPasses = seatPassFileReader.getSeatPasses();
        StudyCafeSeatPass studyCafeSeatPass = seatPasses.findPassBy(StudyCafePassType.HOURLY).get(0);

        LockerPassProvider lockerPassProvider = new LockerPassFileReader();
        StudyCafeLockerPasses lockerPasses = lockerPassProvider.getLockerPasses();

        // when
        Optional<StudyCafeLockerPass> lockerPassBy = lockerPasses.findLockerPassBy(studyCafeSeatPass);

        // then
        assertThat(lockerPassBy).isEmpty();
    }

    @DisplayName("고정석 이용권을 선택하면 락커 이용권이 있다.")
    @Test
    void doseUseLocker() {
        // given
        SeatPassFileReader seatPassFileReader = new SeatPassFileReader();
        StudyCafeSeatPasses seatPasses = seatPassFileReader.getSeatPasses();
        StudyCafeSeatPass studyCafeSeatPass = seatPasses.findPassBy(StudyCafePassType.FIXED).get(0);

        LockerPassProvider lockerPassProvider = new LockerPassFileReader();
        StudyCafeLockerPasses lockerPasses = lockerPassProvider.getLockerPasses();

        // when
        Optional<StudyCafeLockerPass> lockerPassBy = lockerPasses.findLockerPassBy(studyCafeSeatPass);

        // then
        assertThat(lockerPassBy).isPresent();
    }
}
