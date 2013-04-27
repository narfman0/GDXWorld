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

[![GDXWorld car demo](http://img.youtube.com/vi/ghzrj9eY6AU/0.jpg)]
(http://www.youtube.com/watch?v=ghzrj9eY6AU)

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

A level that is currently being moved around or otherwise edited is in light
gray, whereas a level that has been saved is in white. One may edit a levels
properties or location by left clicking on it, or one may immediately move the
level by holding left shift and click and dragging the level to a new desired
location. During this process the edited level is a gray instead of white to
indicate its fluidity. When the changes are accepted, the level changes to
a single white circle representing its new location.

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
to start populating the world. Note while clicking vertices are being added
to a yet unknown polygon. While a polygon is being edited, it is a lighter
color green, and after accepting the changes or editing a finished polygon,
the polygon will be a lighter green. After finishing a polygon, either by 
creating a new polygon or by editing a pre-existing polygon, explore the 
options. 

Dynamic, Static, and Kinematic (as are other parameters) are Box2d specific
attributes defining mobility. I feel they are self describing and shall thus
cop out and link you to the official manual, which a swarm of brilliant folks
likely put together for this expressed purpose: http://www.box2d.org/manual.html

Take special note of the Name parameter, as you may use it to connect joints.
Accepting the changes adds the polygon, deleting removes the polygon from the
level, and cancel eradicates changes, bringing the user back to a clean state
before the editor was brought up.

A polygon is composed of a set of vertices and a center point around which 
these vertices orient. One may move a whole polygon by holding down the left
shift key (while the edit window is NOT active), left clicking a vertex which 
is part of the polygon, and dragging to a new location. Note that while the
vertex was selected, the center of the polygon will be moved (which is likely
different from the specific vertex selected) and the whole polygon will then be
shifted around the center (rather than the selected vertex). 

Alternatively, if the user wishes to move just one vertex in a polygon, first
select the polygon by clicking one of its vertices, then hold left shift and
left click the vertex to be moved, and drag it to its new desired location. 
When in polygon edit mode, with the polygon window up showing the many vertices
in a windows, one may more easily shift certain vertices.

### Circle
See Polygon above, but this time in circles

### Background
Background mode is the interface through which the user may put down images
that do not get loaded into the physics world upon level load. It is up to
the user to render these, and a reference GDXRenderer is included for using
backgrounds faster.

Background supports parallax scrolling 
http://en.wikipedia.org/wiki/Parallax_scrolling. This is handled primarily
through the "depth" attribute. This attribute will scale the texture in size
and will offset it linearly according to camera position. A depth of 0 is 
on the camera (and this invalid/never visible), 0-1 is the foreground up to
the physics plane, 1 is the physics plane a.k.a. midground, and greater than 1
means the object is being drawn in the background.

To shift the backgrounds, again hold left shift while left click and dragging
the background to move it around freely. Upon releasing left click, the image
will plant in its new home to forever be (hopefully attractively) centered
on the last clicked coordinates.

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

One may, in a similar fashion, move NPC nodes by holding left shift and
dragging towards its new intended location. Upon releasing the mouse, the NPC
should be moved the the new location.

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
the joint to the world, while delete removes the joint currently being edited.

Example: a commonly requested feature is the car motor. A car motor may be
accomplished by first creating a dynamic car body, two dynamic car wheels,
and a platform beneath the car on which it may drive. Following this, the user
should take special note of the RevoluteJoint, which may provide motor 
capabilities. Attach two revolute joints to each wheel, for RWD, the back wheel
may have the motor with the unique revolute joint properties "enable motor",
"max torque", and "motor speed" set to some reasonable figures. At this point,
(when 3 dynamic bodies have been "joined" using two revolute joints), you have
a simple motor drive RWD vehicle. Click live mode to demonstrate the 
capabilities of your sweet ride in gdxworld real time.

Advanced Example: For the slightly more advanced GDXWorlder, you may wish to
employ a quest which takes a trigger "Input" (to grab when the user inputs an 
accelerate keyboard button) and a manifestation "Physics" that causes the motor
to turn on/off. This could yield a dynamic vehicle, and of course it is left up
to the implementing IQuestManifestationExecutor to what degree the car handles. 

### Quest
Each level has a set of quests. The intent is that any number of quests may be
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

A quest may be repeatable, which could be useful for what some may consider
"scripting," such that you have some sort of environmental trigger and 
repeatable physics manifestation. An example could be a distance based trigger
which manifests in a motor turning on for an elevator or similar behavior.

### Light
Light mode creates a world editor interface for the box2dlights 
https://code.google.com/p/box2dlights extension to libgdx. This allows you to 
create three different types of lights, Directional, Point, and Cone. The 
populated ray handler is in the CreateLevelReturnStruct, so caching the result,
updating the matrix, and rendering (after the objects you want affected have 
been rendered). Quick example here: 
https://code.google.com/p/box2dlights/wiki/HelloWorld

One may use opengl 1 or 2 es, however, due to the shading centric nature of
opengl 2, 2.0 is the preferred lighting method. It is strongly encouraged that
the client uses opengl 2 es, so as to take advantage of hardware shader mastery
for great justice.

### Group
Group mode is designed to batch move, import, and export related objects. A 
group is composed of polygons, circles, and joints. In the editor, one may
simply refer to them by name, but when exporting and importing special 
attention must be paid to the "center" attribute. When exporting, all pertinent
vertices, anchors, centers, etc, are saved relative to the center. This means
when the user loads the group again, it will load at 0,0 and may be shifted
after that. 

To create a group, first click the group mode and "add" button. Next, name the
group and list which polygons, circles, and joints are in the group by name, 
comma delimited. For a car, one may make a rectangular body named "body", two
wheels names "front" and "back", and two revolute joints, one with a motor,
named "w1joint" and "w2joint". In the group editor, then, you could name it
"car", in the polygon text field type "body", in the circles text field type
"w1, w2", and in the joints field type "w1joint, w2joint".

To export, hit the export button from the editor window. Select which 
serializer you wish to use, then navigate to a file and hit save. If using the
XML or JSON serializer, you could open that file and note the contents, but for
now make a new level in which you may import. Again in group mode, click the
import button and navigate to the file to load in the same group. Note that the
names are not changed, so you may not currently load multiples of the same body
without odd repercussions since the editor assumes unique names.

Serializers
-----------

GDXWorld supports a variety of serializer plugins. By default, there are three
available. These may be used when serializing items to a file, for instance
when saving the world or exporting a group.

### Java Serializer

The java serializer will save items in a .ser file and and must be
used with things implementing Serializable. This is a binary format and 
therefore small, but is less prone to gracefully recovering from slight changes
in the classes it is serializing. Thus if a world is saved with one version of
GDXWorld, and a later version adds a field, an exception will be thrown and it
will be tough to recover. Be forewarned..!

### XML Serializer

The XML serializer uses reflection to go over every field in the class to be
serialized and will make a node for each. If it is a complex element, it will
in turn reflect that class, and so on (recursively). As a result the worlds are
saved with minimal code. This format is more text, but can be hand edited or 
viewed outside of the world editor.

### JSON serializer

Operates the same was as XML, see above

Project setup/Cold Start
------------------------

Designed to be managed from eclipse, preferably in Fedora 16+ with the egit
plugin, but should work under any environment. To run, navigate to
GDXWorldEditor, right click, Run As, Java Application. 

Uses libGDX nightly snapshot from 2013Apr20
Refer to LICENSE for (admittedly lacking) licensing information
