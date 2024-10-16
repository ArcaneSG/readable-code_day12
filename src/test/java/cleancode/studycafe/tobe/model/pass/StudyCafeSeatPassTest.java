package cleancode.studycafe.tobe.model.pass;

import cleancode.studycafe.tobe.io.provider.SeatPassFileReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeSeatPassTest {

    @DisplayName("CSV 파일정보를 통해 좌석 이용권 리스트를 생성한다")
    @Test
    void getSeatPasses() {
        // given
        SeatPassFileReader seatPassFileReader = new SeatPassFileReader();

        // when
        StudyCafeSeatPasses seatPasses = seatPassFileReader.getSeatPasses();

        // then
        assertThat(seatPasses).isNotNull();
    }

    @DisplayName("시간 단위 이용권을 선택 했을 때 좌석 이용권 리스트를 생성한다")
    @Test
    void getHourlySeatPasses() {
        // given
        SeatPassFileReader seatPassFileReader = new SeatPassFileReader();
        StudyCafeSeatPasses seatPasses = seatPassFileReader.getSeatPasses();

        // when
        List<StudyCafeSeatPass> studyCafeSeatPasses = seatPasses.findPassBy(StudyCafePassType.HOURLY);

        // then
        assertThat(studyCafeSeatPasses).hasSize(6);
    }

    @DisplayName("주 단위 이용권을 선택 했을 때 좌석 이용권 리스트를 생성한다")
    @Test
    void getWeeklySeatPasses() {
        // given
        SeatPassFileReader seatPassFileReader = new SeatPassFileReader();
        StudyCafeSeatPasses seatPasses = seatPassFileReader.getSeatPasses();

        // when
        List<StudyCafeSeatPass> studyCafeSeatPasses = seatPasses.findPassBy(StudyCafePassType.WEEKLY);

        // then
        assertThat(studyCafeSeatPasses).hasSize(5);
    }

    @DisplayName("1인 고정석 이용권을 선택 했을 때 좌석 이용권 리스트를 생성한다")
    @Test
    void getFixSeatPasses() {
        // given
        SeatPassFileReader seatPassFileReader = new SeatPassFileReader();
        StudyCafeSeatPasses seatPasses = seatPassFileReader.getSeatPasses();

        // when
        List<StudyCafeSeatPass> studyCafeSeatPasses = seatPasses.findPassBy(StudyCafePassType.FIXED);

        // then
        assertThat(studyCafeSeatPasses).hasSize(2);
    }


}
