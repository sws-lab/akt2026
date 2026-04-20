package week8.interdemo.spammer.ast;

import week8.interdemo.spammer.Relay;

public record SmsCmd(long number, String name, String msg) implements RelayCommands {

    @Override
    public void send() {
        Relay.sendSMS(number, name, msg);
    }

    @Override
    public String toString() {
        return "SmsCmd{" +
                "number=" + number +
                ", msg='" + msg + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
