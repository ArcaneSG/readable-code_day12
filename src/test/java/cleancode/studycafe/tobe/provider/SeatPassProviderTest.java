package cleancode.studycafe.tobe.provider;

import cleancode.studycafe.tobe.io.provider.SeatPassFileReader;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPasses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SeatPassProviderTest {

    @DisplayName("FileReader 를 통해서 좌석 이용권 리스트 생성한다.")
    @Test
    void getSeatPassesByFile() {
        // given
        SeatPassProvider seatPassProvider = new SeatPassFileReader();

        List<StudyCafeSeatPass> passList = new ArrayList<>();
        passList.add(StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 2, 4000, 0.0));
        passList.add(StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 4, 6500, 0.0));
        passList.add(StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 6, 9000, 0.0));
        passList.add(StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 8, 11000, 0.0));
        passList.add(StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 10, 12000, 0.0));
        passList.add(StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 12, 13000, 0.0));
        passList.add(StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 1, 60000, 0.0));
        passList.add(StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 2, 100000, 0.1));
        passList.add(StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 3, 130000, 0.1));
        passList.add(StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 4, 150000, 0.1));
        passList.add(StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 12, 400000, 0.15));
        passList.add(StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 250000, 0.1));
        passList.add(StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 700000, 0.15));

        StudyCafeSeatPasses initSeatPasses = StudyCafeSeatPasses.of(passList);

        // when
        StudyCafeSeatPasses providerSeatPasses = seatPassProvider.getSeatPasses();

        // then
        for (StudyCafePassType passType : StudyCafePassType.values()) {
            assertThat(providerSeatPasses.findPassBy(passType).stream().toList())
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(initSeatPasses.findPassBy(passType).stream().toList());
        }

    }
}
