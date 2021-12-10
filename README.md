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