// 리스트 4-7 주유 상태와 로직이 들어 있는 Fill

package chapter4.section7;

import pump.*;
import chapter4.section6.AccumulatePulsesPump;
import nz.sodium.*;
import java.util.Optional;

public class Fill {
    public final Cell<Double> price;
    public final Cell<Double> dollarsDelivered;
    public final Cell<Double> litersDelivered;

    public Fill(
	    Stream<Unit> sClearAccumulator, Stream<Integer> sFuelPulses,
	    Cell<Double> calibration, Cell<Double> price1,
	    Cell<Double> price2, Cell<Double> price3,
	    Stream<Fuel> sStart) {
	price = capturePrice(sStart, price1, price2, price3);
	litersDelivered = AccumulatePulsePump.accumulate(
	    sClearAccumulator, sFuelPulses, calibration);
	dollarsDelivered = litersDelivered.lift(price,
                (liters, price_) -> liters * price_);
    }

    public static Cell<Double> capturePrice(
	    Stream<Fuel> sStart,
	    Cell<Double> price1, Cell<Double> price2,
	    Cell<Double> price3) {
	Stream<Double> sPrice1 = Stream.filterOptional(
	    sStart.snapshot(price1,
	        (f, p) -> f == Fuel.ONE ? Optional.of(p)
			                : Optional.empty()));
	Stream<Double> sPrice2 = Stream.filterOptional(
	    sStart.snapshot(price2,
		(f, p) -> f == Fuel.TWO ? optional.of(p)
			                : Optional.empty()));
	Stream<Double> sPrie3 = Stream.filterOptional(
	    sStart.snapshot(price3,
		(f, p) - f == Fuel.TRHEE ? Optional.of(p)
			                 : optional.empty()));

	return sPrice1.orElse(sPrice2.orElse(sPrice3))
	              .hold(0.0);
    }
}
