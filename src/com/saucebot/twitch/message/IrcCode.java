package com.saucebot.twitch.message;

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
    Nosuchnick("401", IrcCodeType.Error),
    Nosuchserver("402", IrcCodeType.Error),
    Nosuchchannel("403", IrcCodeType.Error),
    Cannotsendtochan("404", IrcCodeType.Error),
    Toomanychannels("405", IrcCodeType.Error),
    Wasnosuchnick("406", IrcCodeType.Error),
    Toomanytargets("407", IrcCodeType.Error),
    Noorigin("409", IrcCodeType.Error),
    Norecipient("411", IrcCodeType.Error),
    Notexttosend("412", IrcCodeType.Error),
    Notoplevel("413", IrcCodeType.Error),
    Wildtoplevel("414", IrcCodeType.Error),
    Unknowncommand("421", IrcCodeType.Error),
    Nomotd("422", IrcCodeType.Error),
    Noadmininfo("423", IrcCodeType.Error),
    Fileerror("424", IrcCodeType.Error),
    Nonicknamegiven("431", IrcCodeType.Error),
    Erroneusnickname("432", IrcCodeType.Error),
    Nicknameinuse("433", IrcCodeType.Error),
    Nickcollision("436", IrcCodeType.Error),
    Usernotinchannel("441", IrcCodeType.Error),
    Notonchannel("442", IrcCodeType.Error),
    Useronchannel("443", IrcCodeType.Error),
    Nologin("444", IrcCodeType.Error),
    Summondisabled("445", IrcCodeType.Error),
    Usersdisabled("446", IrcCodeType.Error),
    Notregistered("451", IrcCodeType.Error),
    Needmoreparams("461", IrcCodeType.Error),
    Alreadyregistred("462", IrcCodeType.Error),
    Nopermforhost("463", IrcCodeType.Error),
    Passwdmismatch("464", IrcCodeType.Error),
    Yourebannedcreep("465", IrcCodeType.Error),
    Keyset("467", IrcCodeType.Error),
    Channelisfull("471", IrcCodeType.Error),
    Unknownmode("472", IrcCodeType.Error),
    Inviteonlychan("473", IrcCodeType.Error),
    Bannedfromchan("474", IrcCodeType.Error),
    Badchannelkey("475", IrcCodeType.Error),
    Noprivileges("481", IrcCodeType.Error),
    Chanoprivsneeded("482", IrcCodeType.Error),
    Cantkillserver("483", IrcCodeType.Error),
    Nooperhost("491", IrcCodeType.Error),
    Umodeunknownflag("501", IrcCodeType.Error),
    Usersdontmatch("502", IrcCodeType.Error),
    Connected("001"),
    Hostinfo("002"),
    Serverinfo("003"),
    Myinfo("004"),
    Isupport("005"),

    Privmsg("PRIVMSG"),
    Mode("MODE"),
    Join("JOIN"),
    Part("PART"),
    Ping("PING"),

    // System codes from "jtv" private messages
    Usercolor("USERCOLOR", IrcCodeType.System),
    Specialuser("SPECIALUSER", IrcCodeType.System),
    Emoteset("EMOTESET", IrcCodeType.System),
    Historyend("HISTORYEND", IrcCodeType.System),
    Clearchat("CLEARCHAT", IrcCodeType.System),

    Unknown("000");

    private final String code;
    private final IrcCodeType type;

    IrcCode(final String code) {
        this(code, IrcCodeType.Normal);
    }

    IrcCode(final String code, final IrcCodeType type) {
        this.code = code;
        this.type = type;
    }

    public String code() {
        return code;
    }

    public boolean isError() {
        return type == IrcCodeType.Error;
    }

    public boolean isSystem() {
        return type == IrcCodeType.System;
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
