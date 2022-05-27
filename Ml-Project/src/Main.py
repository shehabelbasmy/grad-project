import os
import warnings

import numpy as np
import pandas as pd
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sklearn.preprocessing import StandardScaler
from sklearn.svm import SVC


# import matplotlib.pyplot as plt
# import seaborn as sns
#
# from sklearn.metrics import classification_report
for dirname, _, filenames in os.walk('/kaggle/input'):
    for filename in filenames:
        print(os.path.join(dirname, filename))
warnings.filterwarnings('ignore')

dataset = pd.read_csv('heart.csv')

le = LabelEncoder()

#this line convert sex column value to zeros(Female) and ones(Male) 
dataset['Sex']=le.fit_transform(dataset['Sex'])

#this line convert ExerciseAngina column value to zeros(No) and ones(Yes) 
dataset['ExerciseAngina']=le.fit_transform(dataset['ExerciseAngina'])

# print(dataset)

dataset=pd.get_dummies(dataset, drop_first=False)
# print(dataset.columns)

#the Result for if the patient has HeartDisease Or not 
heart_dis = dataset['HeartDisease']

#remove the result column(HeartDisease)
dataset = dataset.drop('HeartDisease', axis=1)

dataset.insert(loc=len(dataset.columns), column='HeartDisease', value=heart_dis)

x = dataset.iloc[:, :-1].values
y = dataset.iloc[:, -1].values
# print(x)
# print(y)
x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.25, random_state=27)

sc = StandardScaler()
x_train = sc.fit_transform(x_train)
print(x_test[0])
x_test = sc.transform(x_test)

classifier = SVC(kernel='rbf', random_state=27)

classifier.fit(x_train, y_train)

y_pred = classifier.predict(x_test)

result_np = np.concatenate((y_pred.reshape(len(y_pred), 1), (y_test.reshape(len(y_test), 1))), 1)
result = pd.DataFrame(result_np, columns=['Prediction', 'Real_Value'])
# print(classification_report(y_test, y_pred))


val_score = cross_val_score(estimator=classifier, X = x_train, y=y_train, cv=10)
# print("Accuracy: {:.2f} %".format(val_score.mean()*100))
# print("Std. Dev: {:.2f} %".format(val_score.std()*100))












