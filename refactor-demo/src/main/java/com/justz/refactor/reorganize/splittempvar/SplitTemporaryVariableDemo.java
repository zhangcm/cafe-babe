package com.justz.refactor.reorganize.splittempvar;

/**
 * Split Temporary Variable
 */
public class SplitTemporaryVariableDemo {
    // 初始加速度
    double _primaryForce;
    // 两个力共同作用的加速度
    double _secondaryForce;
    // 质量
    double _mass;
    int _delay;

    double getDistanceTravelled(int time) {
        double result;
        final double primaryAcc = _primaryForce / _mass;
        int primaryTime = Math.min(time, _delay);
        result = 0.5 * primaryAcc * primaryTime * primaryTime;
        int secondaryTime = time - _delay;
        if (secondaryTime > 0) {
            double primaryVel = primaryAcc * _delay;
            double secondaryAcc = (_primaryForce + _secondaryForce) / _mass;
            result += primaryVel * secondaryTime + 0.5 * secondaryAcc * secondaryTime * secondaryTime;
        }
        return result;
    }

}
