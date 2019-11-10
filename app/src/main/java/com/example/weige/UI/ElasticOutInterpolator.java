package com.example.weige.UI;

import android.animation.TimeInterpolator;

class ElasticOutInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float t) {
        if (t == 0) return 0;
        if (t >= 1) return 1;
        float p=.3f;
        float s=p/4;
        return ((float)Math.pow(2,-10*t) * (float)Math.sin( (t-s)*(2*(float)Math.PI)/p) + 1);
    }
}
