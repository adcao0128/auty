package com.auty.modules;

public class BatteryTriggerPluggedIn extends AbstractTrigger<Float>{
    final BatteryApplet batteryApplet;
    @Override
    public boolean handleService() {
        return batteryApplet.status_call_plugged_in();
    }

    public BatteryTriggerPluggedIn(String triggerName, float condition, Applet batteryApplet){
        super(triggerName, condition);
        this.batteryApplet = (BatteryApplet) batteryApplet;
    }

}
