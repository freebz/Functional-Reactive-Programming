// 리스트 9-1 애니메니션에 대한 표현

import nz.sodium.*;
import nz.sodium.time.*;
public interface Animation {
    public Cell<Drawable> create(TimerSystem<Double> sys, Point extents);
}
