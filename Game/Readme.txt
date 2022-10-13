Special Forces game Version 1.0 20/06/2020

GENERAL INFORMATION ABOUT APP
==============================

* There are 3 packages and 1 main java class in this project.

* package Functions contains 3 classes: Animation, Bullet and MC.
	Animation: This class is responsible for animations. It has three parameters: region, frameCount and cycleTime,
		   where region is the texture to be animated. This class divides this texture by framecount and plays
		   every frame every cycleTime.

	Bullet:    Class Bullet is responsible for physics of bullets that the character shoots.

	MC:        This class is responsible for the main character you play for, for his movement and his animation.

* package Levels has 3 classes: Level1, Level2 and Level3. Each class is responsible for 1 level. Full structure and 
  physics of level is built on these classes.

* package Screens contains 5 classes: MainMenuScreen, GameOverScreen, BetweenLevelsScreen, OptionsScreen, LastScreen.
	MainMenuScreen:      This class is responsible for main menu. Main menu contains buttons like "Continue", 
			     "New game", "Options" and "Exit".

	GameOverScreen:      This screen is called when our character dies.

	BetweenLevelsScreen: This screen is called when you complete the level.

	OptionsScreen:       In this screen you can turn on or turn off music or sounds of game, change control button 
			     positions and change size of this buttons.

	LastScreen:	     This screen is called when you complete all levels.

* MyGdxGame - the main class of project. This class contains almost all textures, skins of buttons and fonts. At the 
  start of an app, it will call MainMenuScreen.

==========================================================================================================================

Special Forces game can be reached at:

Voice:   8-708-528-2457
VK page: https://vk.com/hikka_nerd
E-mail:  kogabay@inbox.ru