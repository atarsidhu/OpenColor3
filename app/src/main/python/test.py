
from java import *
import os
import numpy as np
import cv2

def py_print(text):
    name=text
    return name



def pic_func(o):
    a = np.array(o)

    img_buffer = None
    img_buffer=np.asarray(bytearray(a), dtype=np.uint8)
    retval = cv2.imdecode(img_buffer, cv2.IMREAD_COLOR)\

    #nored_image[:,:,2] = np.zeros([retval.shape[0], retval.shape[1]])

    #im_buf_arr = cv2.imencode('.png', retval)
    #byte_im = im_buf_arr.tobytes()


    return a.tobytes()