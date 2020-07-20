All Example runs are objects in the scala package. Example runs 
will pull in the required Scala classes from their respective 
packages. You should be able to run the objects themselves, 
since I've given them all a main method. 

Extra set up to run concurrency examples. Since v 2.10, Scala has 
deprecated their Actors library in favour of Akka's actor 
library. We will be using Akka instead. 

1. Download Akka Actors Library. There are various ways to do this. 
All of which are linked here in their docs. 
https://doc.akka.io/docs/akka/2.4/intro/getting-started.html

2. Add to Project dependencies and project class path. This will differ
depending on the IDE that you are using. I am using IntelliJ so this is 
done by adding via File -> Project Structure -> Dependencies -> Add Library. 

I've added 4 Examples in case you aren't able to get the Akka Actors library to 
run. I had some trouble setting it up myself. Hopefully you do though because 
I worked hard on it :) 



