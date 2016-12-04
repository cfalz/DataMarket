from __future__ import print_function
import random

namefile = open('names.txt')
idlist = open('dataids.txt')
usertypes = ['admin', 'customer']
namelist = []
loginlist = []
typelist = []
devicenamelist = ['Fitbit', 'Nike Wrist'] 


csv = open('data.csv', 'w')

# User table
count = 0
for name in namefile.readlines():
    count+=1
    namelist.append(name) 
    fname = name.split(' ')[0]
    lname = name.split(' ')[1]
    """
    if(count %25 == 0):
        print usertypes[0]
    else:
        print usertypes[1]
    """
    print(devicenamelist[random.randrange(0,2)]) 
    #print name.strip()
    loginlist.append(fname[0] + lname[0:4])
    typelist.append(usertypes[1])

# Data table





