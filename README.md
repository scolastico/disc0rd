# Disc0rd - Community Bot
### [outdated and unsuportet! new version is on the way but unfortunately closed source]
![my badge](https://github.com/scolastico/disc0rd/workflows/Java%20CI/badge.svg)

## Usage
To use the bot go on following site and and add it to your discord server. [Invite Url](https://invite.disc0rd.me/)

Now you have access to following commands:
```
disc0rd/help - Shows this help!
disc0rd/subscribe <username> - Subscribes an user in this channel.
disc0rd/unsubscribe <username> - Unsubscribe an user in this channel.
disc0rd/listSubs - Lists the subs of this channel.
disc0rd/listAllSubs - Lists all subs of this server.
disc0rd/allowAdmin <true/false> - Allow admins control this bot.
```

## Installation
To run the bot execute following command:
``` shell script
docker-compose build && docker-compose up -d
```
Then edit the config (`disc0rd.properties`) and restart the docker container.
