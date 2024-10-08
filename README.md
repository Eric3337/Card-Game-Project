# Chinese Card Game

## By Eric Zhang

This application will allow users to play a fun card game 
with their friends and family. The card game is based off 
on a card game that my parents and family members
played with me when I was a boy. My speculations are that
the card game is not available online since
not many people have even heard of the name of the card
game, which means that an online version of it has 
probably not been made. Therefore, I would like to 
take on that challenge and hopefully make it a multiplayer
game one day. (**however**, for now, it'll just be single player)

The arbitrary list idea would be that  the game is
able to make a new player name/account and there can 
be an arbitrary number of accounts/players in this game. 
For example, having a list of an object 
accounts that has the statistics of players (ex: win/lost ratios)(this one is 
likely to be helpful with multiplayer systems, that's
just my own idea when I want to expand on this project)

The arbitrary list can *also* be the history of moves/games that
the player has done, which can also be arbitrary.

The cards can be represented as a list or a hashset 
where the system can look through it easily.


## User Stories

- As a user, I want to be able to start and play a game of cards.
- As a user, I want to be able to create an account and add played games to it.
- As a user, I want to be able to view the total number of games played,
  and win/lost ratio for each game on my account
- As a user, I want to be able to delete my account.
- As a user, I want to be able to see the leaderboard for the top user accounts based on win/loss ratio.
- As a user, I want to be able to save my current game playing to file (if I choose so).
- As a user, I want to be able to load my current game playing from file (if I choose so).
- As a user, I want to be able to add multiple accounts to an account list.
- As a user, I want to be able to load and save the state of a card game.

Phase 4: Task 2
- Create an account with username "User1", you will get a log that says
"Added user: User1 to list of accounts. at (Day Month Time Timezone 2024)".
- Create an account with username "User2", you will get a log that says
  "Added user: User2 to list of accounts. at (Day Month Time Timezone 2024)".
- Select Leaderboard to log the following: 
"List of accounts used for ordering accounts by win loss ratio at (Day Month Time Timezone 2024)"

Phase 4: Task 3
- I think I would try to create more extend and implementation relationships since there
seems to be many associations going into the Card class and the Account and AccountList class. I would 
definitely have added in better ways for classes, especially the cardapp class to only 
handle the ui. Although I did some refactoring, some of the model class still
used the class MessagePrinter to print out messages that proved to be difficult
when using the model classes for the gui. I think for next time, making classes
and assigning classes their specific roles and nothing else will prove greatly.
Also, I think I would mostly definitely use the design patterns (composite, observer, 
and Singleton) to better the project. In general, reduce association coupling 
, increase cohesion and abstraction.