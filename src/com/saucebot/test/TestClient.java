package com.saucebot.test;

import com.saucebot.client.SauceConnection;
import com.saucebot.client.SauceListener;
import com.saucebot.client.emit.Emit;
import com.saucebot.client.emit.EmitHandler;
import com.saucebot.client.emit.EmitType;

public class TestClient implements SauceListener {

    private SauceConnection sauce;

    private TestClient() {
        sauce = new SauceConnection("ravn.no-ip.org", 8458);
        sauce.registerHandlers(this);
        sauce.connect();

    }

    public static void main(final String[] args) throws Exception {
        new TestClient();
    }

    @EmitHandler(EmitType.CHANNELS)
    public void onChannels(final Emit emit) {
        System.out.println("CHANNELS: " + emit);
    }

    @EmitHandler(EmitType.SAY)
    public void onSay(final Emit emit) {
        System.out.println("SAY: " + emit);
    }

    @EmitHandler(EmitType.UNBAN)
    public void onUnban(final Emit emit) {
        System.out.println("UNBAN: " + emit);
    }

    @EmitHandler(EmitType.BAN)
    public void onBan(final Emit emit) {
        System.out.println("BAN: " + emit);
    }

    @EmitHandler(EmitType.TIMEOUT)
    public void onTimeout(final Emit emit) {
        System.out.println("TIMEOUT: " + emit);
    }

    @Override
    public void onConnect() {
        Emit reg = new Emit(EmitType.REGISTER);
        reg.put("type", "chat");
        reg.put("name", "JavaClient");
        sauce.write(reg);
    }

    @Override
    public void onDisconnect() {
        System.out.println("Closed!");
    }

    @Override
    public void onEmit(final Emit emit) {
        // TODO Auto-generated method stub

    }
}
