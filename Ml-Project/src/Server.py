import os
import warnings

from flask import Flask , request
from flask.wrappers import Response

import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sklearn.preprocessing import StandardScaler
from sklearn.svm import SVC


for dirname, _, filenames in os.walk('/kaggle/input'):
    for filename in filenames:
        print(os.path.join(dirname, filename))
warnings.filterwarnings('ignore')

app= Flask(__name__)

@app.route('/',methods=["POST"]) 
def predict():
    
    dataset = pd.read_csv('heart.csv')
    le = LabelEncoder()
    
    #this line convert sex column value to zeros(Female) and ones(Male) 
    dataset['sex']=le.fit_transform(dataset['sex'])
   
    #this line convert ExerciseAngina column value to zeros(No) and ones(Yes) 
    dataset['exerciseAngina']=le.fit_transform(dataset['exerciseAngina'])

    dataset=pd.get_dummies(dataset, drop_first=False)
    
    # myPatient=pd.get_dummies(pd.DataFrame(request.json), drop_first=False)
    myPatient=pd.json_normalize(request.json)

    myPatient=pd.get_dummies(myPatient, drop_first=False)
    #the Result for if the patient has HeartDisease Or not 
    heart_dis = dataset['HeartDisease']
    
    #remove the result column(HeartDisease)
    dataset = dataset.drop('HeartDisease', axis=1)
    
    datasetColumn = dataset.columns.difference(myPatient.columns)
    
    myPatient[datasetColumn] = [0,0,0,0,0,0,0]
    
    dataset.insert(loc=len(dataset.columns), column='HeartDisease', value=heart_dis)
    
    x = dataset.iloc[:, :-1].values
    y = dataset.iloc[:, -1].values
    
    x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.25, random_state=27)
    
    sc = StandardScaler()
    x_train = sc.fit_transform(x_train)
    
    x_test = sc.transform(myPatient)
    
    classifier = SVC(kernel='rbf', random_state=27)
    
    classifier.fit(x_train, y_train)

    y_pred = classifier.predict(x_test)

    r = Response(response=str(y_pred[0]), status=200, mimetype="application/text")
    r.headers["Content-Type"] = "application/json ; charset=utf-8"
    
    return r
 
if __name__=="__main__":
    app.run(debug=True)
    