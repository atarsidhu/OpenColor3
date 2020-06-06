import cv2
from wand.image import Image
#from wand.display import display
#import numpy as np
from matplotlib import pyplot as plt
#cv2.__version__

"""
# This is showing some promise for the changing of the images by shifting the colors contained in them.
# It requires that you install ImageMagick in order to use the tools https://imagemagick.org/
# I need to figure out a few things with this such as the full significance of rightshift and leftshift and
# the impacts of changes of value. The channels are RGB 
# 
# The best part of this is that when the colors are changed it shifts the overall color without overwriting the 
# image and making the image color opaquely 
# The other thing I have to figure out is how to control return the changed image to the app for display
# Also need to figure out how to get the app slider values to work with the "operatoor", "value" and "channels" input
# 
# In order to see the resulting image open it from your virtual env where you are running the script from
# Wand and ImageMagick have some different syntax and I just figured this part out last night. 
# I found ImagMagick as a result of Mandeep directing me to http://www.vischeck.com/daltonize/ They use ImageMagick
# with what they do 

# You can also look up Daltonization Algorithm I am just now looking into this as well

"""
original_image=cv2.imread('Ishihara-Plate-05-38.jpg')

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
def shiftImage(filePathName, c1Shift, c1Value, c1Color,c2Shift, c2Value, c2Color):
        with Image(filename=filePathName) as img:
                # B >> 1
                #if shift == 1:
                img.evaluate(operator=c1Shift, value=c1Value, channel=c1Color)
                #else:
                #img.evaluate(operator='leftshift', value=value, channel=c2Color)

                # R << 1
                #if shift == 1:
                img.evaluate(operator=c2Shift, value=c2Value, channel=c2Color)
                #else:
                        #img.evaluate(operator='rightshift', value=1, channel='green')

                #img.evaluate(operator='rightshift', value=1, channel='red')
                img.format='png'
                img.save(filename='plate6.png')
                #display(img)
                return Image(img)



image = shiftImage('Ishihara-Plate-05-38.jpg',"rightshift",1.5,"green","rightshift",1,"red")

fig=plt.figure()
ax1 = fig.add_subplot(1,2,1)
ax1.imshow(cv2.cvtColor(original_image, cv2.COLOR_BGR2RGB))
ax1.set_title('Original Image')
ax2 = fig.add_subplot(1,2,2)
ax2.imshow(image)
ax2.set_title('Modified Image')

#plt.imshow(image)
plt.show()



