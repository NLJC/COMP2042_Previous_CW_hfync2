WORK CONDUCTED DURING EXTENSION AND MAINTENANCE

1. extension, decided to change color of rectangle in start screen to opaque so adding an image would be easier.

2. extension, attempted to add a background image using paintComponent...failed. It worked, but it didn't work properly(image appeared in another frame). Will try other methods first, and try to fix this method if unsuccessful. 
resource link: https://www.tutorialspoint.com/how-to-add-background-image-to-jframe-in-java

3. extension, attempted to add a background image using drawImage...success. Will skip re-attempting no.2. Also, forgot to add link before committing to github, will include in next commit. 
resource link: https://www.javatpoint.com/Displaying-image-in-swing

4. maintenance, changed some variable names to make them easier to understand, changed debug menu activation key to F1 only.

5. extension, added info button. Tried to use the developer's method of adding frames, but couldn't manage to, so used a different method I'm familiar with instead. Also, added 2 more levels to the game, but still using the old bricks because I'm currently unable to figure out how they work. May come back to this later to try again.

6. extension, attempted to add high score as a global variable using public static...failed. Will try something else.

7. extension, added highscore and currentscore as private variables in GameBoard.java, also created a file named highscorelist to save the highscore. Changed the message after every brickbreaker round to show highscore and currentscore. Added a display for highscore and currentscore in owner, so it displays in both startmenu and in game.

8. maintenance, added Maven's files in, then added JUnit in. Also removed the permanent display for highscore because it wasn't functioning properly after adding Maven. Finally, removed test package because it was useless.

9. maintenance, auto-generated javadocs for all methods. Then proceeded to rename all names I didn't like or feel were self-explanatory, and added comments to all Javadocs. I also renamed GameBoard.java to GamePanel.Java, since it deals with the JPanel and renamed Wall.Java to GameBoard.Java, since it deals with everything within the rectangle where the game takes place.

10. maintenance, moved some lines of code into their own methods due to them doing different things than the rest of the code, and removed some code that was unecessary such as the duplicate code that was also in setImpact(now renamed to checkImpact). Also the setter for x and y velocity of the ball in debug console. 

12. extension, added 2 new bricks, ToughBrick and HellBrick, which were inspired by the previous bricks(also my proudest achievement being hellbrick). ToughBrick is a combination of CementBrick and SteelBrick, inheriting both their properties, but it still cracks even when it doesn't take damage, to give the impression that you're slowly whittling it down. HellBrick, on the other hand, was inspired by the repair function. When I saw it, I decided to add it to a brick(namely ToughBrick) so that it would randomly repair itself sometimes when u hit it. It successfully made for a brick that I personally find very fun(also annoying).

13. maintenance, I didn't like how the old InfoBoard was basically seperated from the game, so I rebuilt it based off of HomeMenu to look very similar in appearance and function to HomeMenu.

14. maintenance, the ball sometimes started at too slow a speed for my liking, so I changed the minimum speed to be a little higher.