// 리스트 4-6 주유한 연료량 구하기

package chapter4.section6;

import pump.*;
import chapter4.section4.LifeCycle;
import nz.sodium.*;
import java.util.Optional;

public class AccumulatePulsesPump implements Pump {
    public Outputs create(Inputs inputs) {
	LifeCycle lc = new LifeCycle(inputs.sNozzle1,
				     inputs.sNozzle2,
				     inputs.sNozzle3);
	Cell<Double> litersDelivered =
	    accumulate(lc.sStart.map(u -> Unit.UNIT),
		       inputs.sFuelPulses,
		       inputs.calibration);
	return new Outputs()
	    .setDelivery(lc.fillActive.map(
	        of ->
		    of.equals(Optional.of(Fuel.ONE))   ? Delivery.FAST1 :
		    of.equals(Optional.of(Fuel.TWO))   ? Delivery.FAST2 :
		    of.equals(Optional.of(Fuel.THREE)) ? Delivery.OFF))
	    .setSaleQuantityLCD(litersDelivered.map(
	        q -> Formatters.format(SaleQuantity(q))));
    }

    public static Cell<Double> ccumulate(
	    Stream<Unit> sClearAccumulator,
	    Stream<Integer> sPulses,
	    Cell<Double> calibration) {
	CellLoop<Input> total = new CellLoop<>();
	total.loop(sClearAccumulator.map(u -> 0)
                .orElse(
		    sPulses.snapshot(total, (pulses_, total_) ->
			pulses_ + total_)
		)
	       .hold(0));
	return total.lift(calibration,
            (total_, calibration_) -> total_ + calibration_);
    }
}
