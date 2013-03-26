GDXWorld
========

GDXWorld is a 2D world editor for libGDX. The intention is to provide not only
a framework to provide physics information to populate box2d worlds, but also
give parameters for the interaction of the shapes therein through the use of
joints, define NPC information with pathing, overworld map, and quests.

Overview
--------

GDXWorld is not simply a level editor, but a world and environment creator. It
starts off in a overworld type editor, defining the number of levels, their
relative locations as 2D world coordinates, and the required level progression
before reaching particular levels. In world editor mode, one may save or load
the world in a java serialized fashion.

The level editor has a number of modes focused on effective and efficient world
creation. The user would click on the check box representing the mode to be in,
then use it appropriately. In some cases, a window will come up immediately,
otherwise the user may have to click around to invoke a window.

World Editor
------------

The world editor is what is first seen after deciding to build a new world. A
conspicuously black screen meets the user, and after clicking in the void,
the user is presented with the overworld level editor window. Here, one may
redefine the levels world coordinate location by clicking on the map. After
hitting accept, the level is "committed" to the current world and the node 
shows up in the black.

To edit a level, click the node within the radius of the circle. The level 
editor will pop up again. The user may now delete it, causing the node to
disappear, edit, going into level edit mode, cancel changes, or accept
current changes.

The world editor has a basic camera to manage navigation lest the users worlds
grow past the meager extents of a 1080p screen. To pan the camera, use the
left/right/up/down arrow keys. To zoom out, roll back on the mouse wheel. To
zoom in, roll in on the mouse wheel. 

Level Editor
------------

After selecting a level node to edit, thus entering the level editor screen,
the user will not a window in the top left labeled "Level Editor" with a number
of check boxes. These check boxes represent the mode of the level editor.

### Polygon
Polygon mode is the most basic type of mode. The user may create, edit, or 
delete polygons in this mode. From a clean level, start clicking on the void
to start populating the world. While the void does not show live updates, note
vertices are being added to a yet unknown polygon. After clicking a box-like
polygon, explore the options. 

Dynamic, Static, and Kinematic (as are other parameters) are Box2d specific
attributes defining mobility. I feel they are self describing and shall thus
cop out and link you to the official manual, which a swarm of brilliant folks
likely put together for this express purpose: http://www.box2d.org/manual.html

Take special note of the Name parameter, as you may use it to connect joints.
Accepting the changes adds the polygon, deleting removes the polygon from the
level, and cancel eradicates changes, bringing the user back to a clean state
before the editor was brought up.

### Circle
See Polygon above, but this time in circles

### Background
Background mode is the interface through which the user may put down images
that do not get loaded into the physics world upon level load. It is up to
the user to render these, and a reference GDXRenderer is included for using
backgrounds faster.

Background support parallax scrolling 
http://en.wikipedia.org/wiki/Parallax_scrolling. This is handled primarily
through the "depth" attribute. This attribute will scale the texture in size
and will offset it linearly according to camera position. A depth of 0 is 
on the camera (and this invalid/never visible), 0-1 is the foreground up to
the physics plane, 1 is the physics plane a.k.a. midground, and greater than 1
means the object is being drawn in the background. The reference implementation
code for offsetting according to depth is the following line:
xy.sub(camera.position.x, camera.position.y).div(scale)

### NPC
NPC editor defines what we view as "keys" to other properties. After naming 
said npc, the behavior could be used as a reference to the a.i. (in my case
behavior tree), strictly up to the client. The path is used to denote to the
NPC where they should traverse within the level. The path is defined in the
next mode, and this is simply a named reference. Resource was intended to be
the animation set, model, or otherwise ui/graphics resource one will use to
render or visually represent the NPC. Faction is an open-ended string, to be
interpreted by the client, ideally indicating friend, foe, and any level in
between.

### Path
Path indicates the route an NPC should work in-game. It is a named reference
and referred to by NPC

### Joint
Joints are a more advanced tool used to define the interaction of two bodies.
Again I will refer you to http://www.box2d.org/manual.html Chapter 8 on joints.
The joint editor window will pop up, listing the possible joints. Currently,
only revolute, distance, and weld joints are available. After clicking one of
these, then hitting the new button, the specific editor type window will pop
up.

If the specific type window demands a body, then use the named reference you
used for the circle/polygon. Often, clicking on the map will fill in anchor 
coordinates or similar as a convenience method. Clicking create will commit
the joint to the world, while delete removes the joint currently being edited

### Quest
Each level has a set of quests. The intent as that any number of quests may be
active simultaneously, as seen in the reference GDXQuestManager and 
corresponding unit test. Please take note of that while reading the following.

A quest is composed of four parts: 
1 Name, being the primary index other quests use to refer to prerequisites
2 Prerequisites, which are dependent quests that must be finished before the
current quest is active, 
3 Trigger, which indicates the quest should go from active to complete
4 Manifestation, which describes the manner in which the transition from
active to complete is represented, be it visually, in the physics environment,
or in another similarly stealthy and cool manner. 

### Light
Light mode creates a world editor interface for the box2dlights 
https://code.google.com/p/box2dlights extension to libgdx. This allows you to 
create three different types of lights, Directional, Point, and Cone. The 
populated ray handler is in the CreateLevelReturnStruct, so caching the result,
updating the matrix, and rendering (after the objects you want affected have 
been rendered). Quick example here: 
https://code.google.com/p/box2dlights/wiki/HelloWorld

Project setup/Cold Start
------------------------

Designed to be managed from eclipse, preferably in Fedora 16+ with the egit
plugin, but should work under any environment. To run, navigate to
GDXWorldEditor, right click, Run As, Java Application. 

Uses libGDX nightly snapshot from 2012Jan12
Refer to LICENSE for (admittedly lacking) licensing information
