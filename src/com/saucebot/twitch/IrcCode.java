package com.saucebot.twitch;

import java.util.HashMap;
import java.util.Map;

public enum IrcCode {

    Tracelink("200"),
    Traceconnecting("201"),
    Tracehandshake("202"),
    Traceunknown("203"),
    Traceoperator("204"),
    Traceuser("205"),
    Traceserver("206"),
    Tracenewtype("208"),
    Statslinkinfo("211"),
    Statscommands("212"),
    Statscline("213"),
    Statsnline("214"),
    Statsiline("215"),
    Statskline("216"),
    Statsyline("218"),
    Endofstats("219"),
    Umodeis("221"),
    Statslline("241"),
    Statsuptime("242"),
    Statsoline("243"),
    Statshline("244"),
    Statsconn("250"),
    Luserclient("251"),
    Luserop("252"),
    Luserunknown("253"),
    Luserchannels("254"),
    Luserme("255"),
    Adminme("256"),
    Adminloc1("257"),
    Adminloc2("258"),
    Adminemail("259"),
    Tracelog("261"),
    Localusers("265"),
    Globalusers("266"),
    None("300"),
    Away("301"),
    Userhost("302"),
    Ison("303"),
    Unaway("305"),
    Nowaway("306"),
    Whoisuser("311"),
    Whoisserver("312"),
    Whoisoperator("313"),
    Whowasuser("314"),
    Endofwho("315"),
    Whoisidle("317"),
    Endofwhois("318"),
    Whoischannels("319"),
    Liststart("321"),
    List("322"),
    Listend("323"),
    Channelmodeis("324"),
    Notopic("331"),
    Topic("332"),
    Inviting("341"),
    Summoning("342"),
    Version("351"),
    Whoreply("352"),
    Namreply("353"),
    Links("364"),
    Endoflinks("365"),
    Endofnames("366"),
    Banlist("367"),
    Endofbanlist("368"),
    Endofwhowas("369"),
    Info("371"),
    Motd("372"),
    Endofinfo("374"),
    Motdstart("375"),
    Endofmotd("376"),
    Youreoper("381"),
    Rehashing("382"),
    Time("391"),
    Usersstart("392"),
    Users("393"),
    Endofusers("394"),
    Nousers("395"),
    Nosuchnick("401", true),
    Nosuchserver("402", true),
    Nosuchchannel("403", true),
    Cannotsendtochan("404", true),
    Toomanychannels("405", true),
    Wasnosuchnick("406", true),
    Toomanytargets("407", true),
    Noorigin("409", true),
    Norecipient("411", true),
    Notexttosend("412", true),
    Notoplevel("413", true),
    Wildtoplevel("414", true),
    Unknowncommand("421", true),
    Nomotd("422", true),
    Noadmininfo("423", true),
    Fileerror("424", true),
    Nonicknamegiven("431", true),
    Erroneusnickname("432", true),
    Nicknameinuse("433", true),
    Nickcollision("436", true),
    Usernotinchannel("441", true),
    Notonchannel("442", true),
    Useronchannel("443", true),
    Nologin("444", true),
    Summondisabled("445", true),
    Usersdisabled("446", true),
    Notregistered("451", true),
    Needmoreparams("461", true),
    Alreadyregistred("462", true),
    Nopermforhost("463", true),
    Passwdmismatch("464", true),
    Yourebannedcreep("465", true),
    Keyset("467", true),
    Channelisfull("471", true),
    Unknownmode("472", true),
    Inviteonlychan("473", true),
    Bannedfromchan("474", true),
    Badchannelkey("475", true),
    Noprivileges("481", true),
    Chanoprivsneeded("482", true),
    Cantkillserver("483", true),
    Nooperhost("491", true),
    Umodeunknownflag("501", true),
    Usersdontmatch("502", true),
    Myinfo("004"),
    Isupport("005"),

    Privmsg("PRIVMSG"),
    Mode("MODE"),
    Ping("PING"),

    Unknown("000");

    private final String code;

    private final boolean isError;

    IrcCode(final String code) {
        this(code, false);
    }

    IrcCode(final String code, final boolean isError) {
        this.code = code;
        this.isError = isError;
    }

    public String code() {
        return code;
    }

    public boolean isError() {
        return isError;
    }

    @Override
    public String toString() {
        return "IrcCode[" + code + ":" + name() + "]";
    }

    private static Map<String, IrcCode> codeMap;

    public static IrcCode getForCode(final String code) {
        IrcCode ircCode = codeMap.get(code);
        return ircCode != null ? ircCode : Unknown;
    }

    private static void buildCodeLookupMap() {
        codeMap = new HashMap<String, IrcCode>();
        for (IrcCode ircCode : values()) {
            codeMap.put(ircCode.code(), ircCode);
        }
    }

    static {
        buildCodeLookupMap();
    }

}
