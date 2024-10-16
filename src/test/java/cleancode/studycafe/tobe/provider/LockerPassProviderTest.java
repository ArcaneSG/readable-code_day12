package cleancode.studycafe.tobe.provider;

import cleancode.studycafe.tobe.io.provider.LockerPassFileReader;
import cleancode.studycafe.tobe.io.provider.SeatPassFileReader;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPasses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LockerPassProviderTest {
    @DisplayName("FileReader 를 통해서 락커 이용권 리스트 생성한다.")
    @Test
    void createLockerPassesByFile() {
        // given
        SeatPassProvider seatPassProvider = new SeatPassFileReader();
        List<StudyCafeSeatPass> studyCafeSeatPasses = seatPassProvider.getSeatPasses().findPassBy(StudyCafePassType.FIXED);

        LockerPassProvider lockerPassProvider = new LockerPassFileReader();
        List<StudyCafeLockerPass> passList = new ArrayList<>();
        passList.add(StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4 , 10000));
        passList.add(StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12 , 30000));

        StudyCafeLockerPasses initLockerPasses = StudyCafeLockerPasses.of(passList);

        // when
        StudyCafeLockerPasses providerLockerPasses = lockerPassProvider.getLockerPasses();

        // then
        for (StudyCafeSeatPass studyCafeSeatPass : studyCafeSeatPasses) {
            assertThat(providerLockerPasses.findLockerPassBy(studyCafeSeatPass).stream().toList())
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(initLockerPasses.findLockerPassBy(studyCafeSeatPass).stream().toList());
        }
    }
}
