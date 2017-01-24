import pandas as pd
import numpy as np
from sklearn import tree
from sklearn.tree import DecisionTreeClassifier, export_graphviz
import random
import math

from sklearn.externals import joblib
import time
sum = 0.0
# for num in range(0,100):
start = time.clock()
cr_mod = joblib.load('test_model.pkl')
test_X=pd.DataFrame({'Frequency':1, 'Ownership':371, 'Frequency_t':1,'Recency':345, 'Mileage':512, 'Model':111,'Visit_per_year':0.98},index=[0])
predicted_X = cr_mod.predict_proba(test_X)[:,0]
# print(predicted_X)
end = time.clock()
sum += (end - start)
print (sum)

