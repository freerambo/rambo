
print ("in python hello world!")


from os  import popen
from sys import stdin

ps = popen("C:/WINDOWS/system32/tasklist.exe","r")
pp = ps.readlines()
ps.close()

# wow, look at the robust parser!
pp.pop(0)       # blank line
ph = pp.pop(0)  # header line
pp.pop(0)       # ===

print ("%d processes reported." % len(pp))
print ("First process in list:")
print (pp[0])

stdin.readline()
