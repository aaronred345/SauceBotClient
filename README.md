SauceBotClient
==============

*SauceBot Client for Twitch Messaging Interface(TMI)*

Dependencies
------------
* Gson
* JUnit (for testing)


How to run
----------

* Windows:
```
java -cp bin;lib\* com.saucebot.client.SauceBot <config file>
```

* Linux:
```
java -cp bin:lib\* com.saucebot.client.SauceBot <config file>
```

**Note:** `<config file>` is the path to the JSON-formatted configuration file.


Configuration file
-------------------

**Format:**
```json
{
    "server": {
        "host": address,
        "port": port
    },
    
    "accounts": {
        username: password,
        username: password,
        ...
    }
}
```