
# Topgg-CrowdinBot

Top.gg Crowdin-Stats Discord bot used on discord.gg/dbl to provide stats about current site translations ([topgg](https//https://crowdin.com/project/topgg) / [topgg-entities](https://crowdin.com/project/topgg-entities)) on Crowdin by using the Crowdin API.

## Running the bot

#### Gradle arguments required

1. [Discord Bot API Token](https://discord.com/developers)
2. [Crowdin Personal API Access Token](https://crowdin.com/settings#api-key)


### Linux

    git clone repoUrl
    cd repoUrl
    ./gradlew run --args='DISCORD_BOT_TOKEN CROWDIN_ACCESS_TOKEN'
    
### Windows

    git clone repoUrl
    cd repoUrl
    gradlew.bat run --args='DISCORD_BOT_TOKEN CROWDIN_ACCESS_TOKEN'

## Used Libraries
- [Javacord](https://github.com/Javacord/Javacord)
- [Javacord-CommandHandler](https://github.com/Hopeful-Developers/Javacord-CommandHandler)
- [OkHttp](https://github.com/square/okhttp)
- [org.json](https://github.com/stleary/JSON-java)
