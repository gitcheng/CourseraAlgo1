# -*- coding: utf-8 -*-
# <nbformat>3.0</nbformat>

# <codecell>

import numpy as np
import matplotlib.pyplot as plt

# <codecell>

 256     0.000
      512     0.002
     1024     0.009
     2048     0.043
     4096     0.205
     8192     0.939
    16384     4.313
    32768    19.816
    65536    92.901
   131072   429.157
   262144  1990.135

# <codecell>

t= np.array([0.000, 0.002, 0.009, 0.043, 0.205, 0.939, 4.313, 19.816, 92.901, 429.157, 1990.135])
n= np.array([256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144])
#t= np.array([0.000, 0.001, 0.010, 0.098, 1.135, 10.224, 102.345, 927.636, 9012.235])
#n= np.array([32,  64,  128,  256,  512, 1024, 2048, 4096, 8192])
print n

# <codecell>

logt= np.log(t)
logn= np.log(n)
print logt
print logn

# <codecell>

plt.plot(logn[1:], logt[1:])

# <codecell>

logn= logn[1:]
logt= logt[1:]

# <codecell>

# slope = ( (xy).mean() - x.mean()*y.mean() ) / ( (x**2).mean() - (x.mean())**2)
slope= ((logn*logt).mean() - logn.mean()*logt.mean()) / ( (logn**2).mean() - (logn.mean())**2)
print slope

# <codecell>

print (logt[-1]-logt[0])/(logn[-1]-logn[0])

# <codecell>

# Step by step slopes
slps= (logt[1:]-logt[:-1])/(logn[1:]-logn[:-1])
print slps
print slps.mean()

# <codecell>


# <codecell>


# <codecell>

range(1,3,1)

# <codecell>

def grow(N):
    sum = 0
    i = 1
    while i <= N:
        for j in range(1, i+1):
            sum= sum+1
        i= i*3
    return sum

# <codecell>

for i in range(1,30):
    N= pow(2,i)
    print N, grow(N)

# <codecell>


