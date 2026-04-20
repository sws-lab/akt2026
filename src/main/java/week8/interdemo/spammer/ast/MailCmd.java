package week8.interdemo.spammer.ast;

import week8.interdemo.spammer.Relay;

public record MailCmd(String address, String name, String msg) implements RelayCommands {
    @Override
    public void send() {
        Relay.sendMail(address, name, msg);
    }

    @Override
    public String toString() {
        return "MailCmd{" +
                "address='" + address + '\'' +
                ", msg='" + msg + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
