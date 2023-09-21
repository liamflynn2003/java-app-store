# java-app-store
1st Year, Semester 2 Final Assignment:

A java application that can create, store and display information on apps and their developers to arrays.

--Overview--
The program uses an array of App superclass with subclasses of Education Apps, Game Apps and Productivity Apps to contain information on apps.
Developers of apps are contained in their own array, with objects that contain each Developer's information.
Ratings of apps are also contained in an array, which are contained in a List<> in each App object.

App Superclass Values: Name, Version, Size, Developer, Cost, Ratings
Developer Values: Developer name, Developer website
Rating Values: Number of Stars, Rater Name, Rating Comment

Users can save and load their Apps and Developers using XStream.

--Application Usage--
Users navigate the AppStore using menus displayed in the System Output by choosing options corresponding to numbers. 
