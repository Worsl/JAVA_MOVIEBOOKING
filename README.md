# MOBLIMA
Done by Group SS11_GP3 OODP Course project for a movie booking application.

This project can be found on [Github](https://github.com/Worsl/OODP_SS11_GP3)
A demo for this project can be found on [Youtube](https://www.youtube.com/watch?v=yUlwvRwmYz4)

# Contributions
This project could not have been possible without the following:
1. [Cheng](https://github.com/Worsl)
2. [Jeremy](https://github.com/iiJoe)
3. [Rhys](https://github.com/Rhys-Wong)
4. [Ee Chern](https://github.com/Pistato)
5. [Jason](https://github.com/JasonYifei)

# Table of Contents

1. [How to Run](#howToRun)
2.  [Data CSV Files Format](#orgc5c4c26)
    1.  [Cineplex](#orgea1e4e2)
    2.  [Cinema](#orga1115ab)
    3.  [Movie](#org8075385)
    4.  [Review](#org8015385)
    1.  [Session](#sessionscsv)
    2.  [Booking](#bookingscsv)
    3.  [Ticket](#ticketscsv)
    4.  [Users](#userscsv)

<a id="howToRun"></a>
# How to Run
The code base is stored in the `/src` directory, which is where the applications runs on. There are two main methods for the respective portals (Moviegoer & Admin), so the correct entry point has to be updated

## Running on IDE
Open a new project where the root of the project is at `/src`

## Running on Command Line
Change the directory to `/src` and compile all the files
```
cd ./src
javac -cp lib/\* $(find . -name '*.java') -d .
java -cp lib/\*: $PROGRAM
```
Replace `$PROGRAM` with Moviegoer or Admin

<a id="orgc5c4c26"></a>

# Data CSV Files Format
The data for this application is stored as CSV files in the `/src/data` subdirectory. The following describes how each resource are stored in CSV.

<a id="orgea1e4e2"></a>

## Cineplex

```
<Name>,<Address>,<Opening Hours>,<Closing Hours>
```

<a id="orga1115ab"></a>

## Cinema

```
<Code>,<Class>,<Cineplex Name>
```

<a id="org8075385"></a>

## Movie

```
<Title>,<Showing Status>,<Synopsis>,<Director>,<Cast>,<Type>,<Duration>,<Content Rating>
```

<a id="org8015385"></a>

## Review

```
<RatingScore>,<Movie Title>,<Reviewer Name>,<Reviewer Email>,<Reviewer Mobile>,<Comment>
```

<a id="sessionscsv"></a>

## Session

```
<Cinema Code>,<Movie Title>,<Session Time Slot>,<Session Id>
```

<a id="bookingscsv"></a>

## Booking

```
<Transaction ID>,<Session Id>,<Booker's name>
```

<a id="ticketscsv"></a>

## Ticket

```
<Transaction ID>,<Seat Number>,<Ticket Type>
```
<a id="userscsv"></a>

## Users

```
<User Name>,<User Email>,<User Mobile>,<User Password>
```
