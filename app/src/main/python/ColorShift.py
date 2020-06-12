
#from java import *
#from wand.image import Image
'''
#@param filePathName, c1Shift, c1Value, c1Color, c2Shift, c2Value, c2Color
# The filePathName - just like it indicates requires the filename and path
# The c1Shift and c2Shift - This must be either "rightshift" or "leftshift"
# The c1Value and c2 Value - 0 - 10 I am assuming as I have not gone beyond 2 I will try
#   this later in order to determine upper and lower bounds
# The c1Color and c2Color - "red", "green", "blue" These are the colors that we can do
    the shifting on.
# As I write this there are a number of other values I need to check: Is 0 a valid lower
    bound, Can we do single image evaluation and what are the effects,
    
'''
from android.media import Image


def shiftImage(filePathName, c1Shift, c1Value, c1Color,c2Shift, c2Value, c2Color):
    with Image(filename=filePathName) as img:
        img.evaluate(operator=c1Shift, value=c1Value, channel=c1Color)
        img.evaluate(operator=c2Shift, value=c2Value, channel=c2Color)
        img.format='png'
        img.save(filename='modified.png')
        #display(img)
        return Image(img)


