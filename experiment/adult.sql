SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 1166219 AND `fnlwgt` <= 1443081)
       AND (   `occupation` = 'Machine-op-inspct'
            OR `occupation` = 'Armed-Forces')
       AND (`race` = 'Amer-Indian-Eskimo')
       AND (   `native-country` = 'Scotland'
            OR `native-country` = 'Yugoslavia'
            OR `native-country` = 'Holand-Netherlands')
       AND `education-num` >= `capital-gain`
       AND `fnlwgt` + `education-num` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 64 AND `age` <= 73)
       AND (`workclass` = 'Without-pay')
       AND (`marital-status` = 'Never-married')
       AND `age` + `capital-loss` >= `hours-per-week`
       AND `fnlwgt` * `capital-loss` <= `hours-per-week`
       AND `education-num` * `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 12 AND `education-num` <= 16)
       AND (`marital-status` = 'Divorced')
       AND (`capital-loss` >= 144 AND `capital-loss` <= 144)
       AND `age` <= `education-num`
       AND `age` + `fnlwgt` >= `capital-gain`
       AND `fnlwgt` - `capital-gain` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 13 AND `education-num` <= 16)
       AND (`occupation` = 'Exec-managerial')
       AND (`race` = 'Black')
       AND `age` + `capital-gain` <= `hours-per-week`
       AND `age` + `education-num` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`capital-gain` >= 54739 AND `capital-gain` <= 59918)
       AND (`hours-per-week` >= 57 AND `hours-per-week` <= 57)
       AND `age` + `education-num` >= `capital-loss`
       AND `fnlwgt` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Self-emp-inc' OR `workclass` = 'State-gov')
       AND (   `education` = 'HS-grad'
            OR `education` = '10th'
            OR `education` = 'Doctorate'
            OR `education` = 'Preschool')
       AND (`relationship` = 'Not-in-family')
       AND `age` + `education-num` <= `capital-loss`
       AND `age` + `education-num` <= `capital-gain`
       AND `fnlwgt` * `education-num` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` = '1st-4th')
       AND (`capital-gain` >= 42725 AND `capital-gain` <= 42725)
       AND (`capital-loss` >= 3678 AND `capital-loss` <= 4489)
       AND `fnlwgt` * `capital-gain` <= `hours-per-week`
       AND `age` * `education-num` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`relationship` = 'Own-child')
       AND (`capital-loss` >= 839 AND `capital-loss` <= 930)
       AND `fnlwgt` + `capital-gain` >= `capital-loss`
       AND `age` + `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 66 AND `age` <= 70)
       AND (   `education` = 'Bachelors'
            OR `education` = '9th'
            OR `education` = '7th-8th')
       AND (`education-num` >= 6 AND `education-num` <= 7)
       AND `age` >= `hours-per-week`
       AND `age` * `fnlwgt` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` = 'Prof-school' OR `education` = '5th-6th')
       AND (`occupation` = 'Farming-fishing')
       AND (`hours-per-week` >= 71 AND `hours-per-week` <= 71)
       AND `fnlwgt` - `capital-gain` <= `hours-per-week`
       AND `fnlwgt` + `education-num` >= `capital-gain`
       AND `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 16 AND `age` <= 16)
       AND (`relationship` = 'Own-child')
       AND `capital-gain` >= `hours-per-week`
       AND `fnlwgt` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 60 AND `age` <= 68)
       AND (`education` = 'HS-grad' OR `education` = 'Masters')
       AND (`education-num` >= 4 AND `education-num` <= 5)
       AND (`capital-gain` >= 87503 AND `capital-gain` <= 96795)
       AND `age` * `capital-gain` >= `capital-loss`
       AND `education-num` - `capital-gain` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` = 'Bachelors' OR `education` = 'Assoc-voc')
       AND (`education-num` >= 10 AND `education-num` <= 11)
       AND (`relationship` = 'Wife')
       AND (`capital-gain` >= 83776 AND `capital-gain` <= 83776)
       AND `age` >= `capital-loss`
       AND `age` * `fnlwgt` >= `capital-loss`
       AND `age` >= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Private')
       AND (`education-num` >= 4 AND `education-num` <= 5)
       AND (`occupation` = 'Priv-house-serv')
       AND (`relationship` = 'Other-relative')
       AND `age` + `education-num` <= `capital-loss`
       AND `age` * `education-num` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 42 AND `age` <= 42)
       AND (`workclass` = 'Self-emp-inc')
       AND (`marital-status` = 'Married-civ-spouse')
       AND (`occupation` = 'Sales')
       AND `education-num` * `capital-gain` <= `hours-per-week`
       AND `age` >= `education-num`
       AND `age` - `fnlwgt` <= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (   `education` = '9th'
            OR `education` = '12th'
            OR `education` = 'Doctorate')
       AND (`race` = 'Amer-Indian-Eskimo')
       AND (`capital-loss` >= 3217 AND `capital-loss` <= 3732)
       AND `age` * `education-num` >= `capital-gain`
       AND `age` * `education-num` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (   `education` = 'Some-college'
            OR `education` = 'HS-grad'
            OR `education` = 'Masters')
       AND (`occupation` = 'Prof-specialty')
       AND (`relationship` = 'Wife')
       AND `age` - `education-num` <= `hours-per-week`
       AND `age` <= `capital-gain`
       AND `age` + `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Federal-gov' OR `workclass` = 'Local-gov')
       AND (`education-num` >= 11 AND `education-num` <= 13)
       AND `age` >= `fnlwgt`
       AND `capital-gain` * `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (   `education` = 'Assoc-voc'
            OR `education` = 'Doctorate'
            OR `education` = 'Preschool')
       AND (`education-num` >= 14 AND `education-num` <= 18)
       AND (`occupation` = 'Adm-clerical' OR `occupation` = 'Armed-Forces')
       AND `age` + `fnlwgt` >= `education-num`
       AND `age` * `education-num` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 39 AND `age` <= 48)
       AND (`education` = 'Preschool')
       AND (`occupation` = 'Tech-support' OR `occupation` = 'Armed-Forces')
       AND (   `native-country` = 'France'
            OR `native-country` = 'Laos'
            OR `native-country` = 'Guatemala'
            OR `native-country` = 'Thailand')
       AND `fnlwgt` <= `hours-per-week`
       AND `fnlwgt` - `education-num` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Without-pay')
       AND (`education-num` >= 4 AND `education-num` <= 5)
       AND (`hours-per-week` >= 29 AND `hours-per-week` <= 29)
       AND `age` + `fnlwgt` <= `education-num`
       AND `age` >= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 11 AND `education-num` <= 12)
       AND (`occupation` = 'Farming-fishing')
       AND (`hours-per-week` >= -1 AND `hours-per-week` <= 8)
       AND `education-num` + `capital-gain` >= `hours-per-week`
       AND `age` + `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'State-gov')
       AND (`education` = 'Doctorate')
       AND (`education-num` >= 15 AND `education-num` <= 15)
       AND `education-num` - `capital-gain` <= `hours-per-week`
       AND `age` + `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (   `occupation` = 'Tech-support'
            OR `occupation` = 'Craft-repair'
            OR `occupation` = 'Prof-specialty'
            OR `occupation` = 'Adm-clerical')
       AND (`race` = 'Amer-Indian-Eskimo')
       AND `fnlwgt` + `capital-loss` <= `hours-per-week`
       AND `age` + `capital-gain` <= `capital-loss`
       AND `fnlwgt` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 28 AND `age` <= 28)
       AND (`education` = '7th-8th')
       AND (`relationship` = 'Husband')
       AND `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` = 'Prof-school' OR `education` = '10th')
       AND (`marital-status` = 'Married-civ-spouse')
       AND (`capital-gain` >= 24858 AND `capital-gain` <= 33922)
       AND `age` * `fnlwgt` <= `capital-gain`
       AND `fnlwgt` * `education-num` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`marital-status` = 'Divorced')
       AND (`relationship` = 'Husband')
       AND (`race` = 'Black')
       AND `age` + `education-num` >= `capital-loss`
       AND `age` + `fnlwgt` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 11 AND `education-num` <= 13)
       AND (   `marital-status` = 'Never-married'
            OR `marital-status` = 'Married-spouse-absent')
       AND (`capital-gain` >= 32907 AND `capital-gain` <= 32907)
       AND `age` * `capital-loss` <= `hours-per-week`
       AND `education-num` - `capital-gain` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 1149765 AND `fnlwgt` <= 1397778)
       AND (`capital-gain` >= 82867 AND `capital-gain` <= 89391)
       AND (`capital-loss` >= 291 AND `capital-loss` <= 755)
       AND `age` >= `fnlwgt`
       AND `fnlwgt` * `education-num` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`occupation` = 'Other-service')
       AND (   `native-country` = 'Germany'
            OR `native-country` = 'France'
            OR `native-country` = 'Taiwan'
            OR `native-country` = 'El-Salvador')
       AND `fnlwgt` * `capital-gain` >= `capital-loss`
       AND `fnlwgt` * `education-num` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`marital-status` = 'Separated')
       AND (`race` = 'Other')
       AND `age` + `fnlwgt` >= `capital-gain`
       AND `capital-loss` >= `hours-per-week`
       AND `education-num` + `capital-gain` >= `hours-per-week`
       AND `age` >= `fnlwgt`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Without-pay')
       AND (`education` = '11th')
       AND (`sex` = 'Female')
       AND (   `native-country` = 'Cambodia'
            OR `native-country` = 'Germany'
            OR `native-country` = 'Cuba'
            OR `native-country` = 'Italy'
            OR `native-country` = 'Mexico'
            OR `native-country` = 'Dominican-Republic'
            OR `native-country` = 'Columbia'
            OR `native-country` = 'Guatemala'
            OR `native-country` = 'Scotland'
            OR `native-country` = 'Thailand'
            OR `native-country` = 'El-Salvador')
       AND `fnlwgt` * `education-num` >= `hours-per-week`
       AND `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` = 'HS-grad' OR `education` = 'Prof-school')
       AND (`marital-status` = 'Never-married')
       AND (`relationship` = 'Own-child')
       AND (`sex` = 'Female')
       AND `education-num` <= `hours-per-week`
       AND `age` * `education-num` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` = 'Bachelors')
       AND (`marital-status` = 'Never-married')
       AND (`race` = 'Other')
       AND `age` * `education-num` <= `hours-per-week`
       AND `education-num` * `capital-gain` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`relationship` = 'Wife')
       AND (`race` = 'White')
       AND (`sex` = 'Female')
       AND `education-num` - `capital-gain` <= `capital-loss`
       AND `age` + `fnlwgt` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 6 AND `education-num` <= 10)
       AND (`capital-loss` >= 3517 AND `capital-loss` <= 3642)
       AND `capital-gain` <= `hours-per-week`
       AND `age` + `education-num` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (   `occupation` = 'Exec-managerial'
            OR `occupation` = 'Prof-specialty'
            OR `occupation` = 'Adm-clerical')
       AND (`capital-gain` >= 53271 AND `capital-gain` <= 66504)
       AND (`capital-loss` >= 3639 AND `capital-loss` <= 4654)
       AND `fnlwgt` * `capital-loss` <= `hours-per-week`
       AND `age` - `education-num` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Never-worked')
       AND (   `occupation` = 'Machine-op-inspct'
            OR `occupation` = 'Protective-serv')
       AND (   `native-country` = 'Outlying-US(Guam-USVI-etc)'
            OR `native-country` = 'India'
            OR `native-country` = 'China'
            OR `native-country` = 'Honduras'
            OR `native-country` = 'Italy'
            OR `native-country` = 'Mexico'
            OR `native-country` = 'Ecuador'
            OR `native-country` = 'Taiwan'
            OR `native-country` = 'Nicaragua'
            OR `native-country` = 'Yugoslavia')
       AND `capital-loss` <= `hours-per-week`
       AND `age` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Self-emp-not-inc' OR `workclass` = 'Without-pay')
       AND (`marital-status` = 'Separated')
       AND (`capital-gain` >= 64592 AND `capital-gain` <= 68475)
       AND (   `native-country` = 'Cambodia'
            OR `native-country` = 'Poland'
            OR `native-country` = 'Mexico'
            OR `native-country` = 'Thailand')
       AND `education-num` + `capital-gain` >= `capital-loss`
       AND `age` + `fnlwgt` <= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 14 AND `age` <= 27)
       AND (   `education` = 'Bachelors'
            OR `education` = 'Some-college'
            OR `education` = 'Assoc-voc'
            OR `education` = '5th-6th')
       AND (`occupation` = 'Protective-serv')
       AND (`sex` = 'Female')
       AND `age` * `fnlwgt` >= `capital-loss`
       AND `capital-gain` + `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'State-gov')
       AND (`occupation` = 'Exec-managerial')
       AND `age` <= `capital-loss`
       AND `fnlwgt` + `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` = '10th')
       AND (`education-num` >= 3 AND `education-num` <= 5)
       AND (`capital-gain` >= 66647 AND `capital-gain` <= 80581)
       AND `fnlwgt` + `education-num` >= `capital-loss`
       AND `age` + `fnlwgt` >= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (   `education` = 'Assoc-acdm'
            OR `education` = '1st-4th'
            OR `education` = 'Doctorate'
            OR `education` = 'Preschool')
       AND (`race` = 'Amer-Indian-Eskimo')
       AND (`sex` = 'Male')
       AND `age` + `fnlwgt` >= `capital-loss`
       AND `education-num` + `capital-loss` <= `hours-per-week`
       AND `fnlwgt` + `education-num` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Private')
       AND (`fnlwgt` >= 1358166 AND `fnlwgt` <= 1448234)
       AND (`education-num` >= 11 AND `education-num` <= 13)
       AND `fnlwgt` * `education-num` <= `capital-loss`
       AND `age` + `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Without-pay')
       AND (`occupation` = 'Craft-repair')
       AND (`capital-loss` >= 3013 AND `capital-loss` <= 3958)
       AND `age` - `fnlwgt` <= `hours-per-week`
       AND `age` - `education-num` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 480289 AND `fnlwgt` <= 749764)
       AND (`native-country` = 'Honduras' OR `native-country` = 'Scotland')
       AND `age` + `fnlwgt` >= `capital-gain`
       AND `age` * `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Without-pay')
       AND (`education` = 'HS-grad' OR `education` = 'Masters')
       AND (`education-num` >= 12 AND `education-num` <= 12)
       AND `fnlwgt` - `capital-gain` <= `capital-loss`
       AND `age` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Without-pay')
       AND (`education` = 'Prof-school')
       AND (`sex` = 'Female');

SELECT AVG(age)
  FROM adult
 WHERE     (`relationship` = 'Other-relative')
       AND (`race` = 'Black')
       AND (`hours-per-week` >= 48 AND `hours-per-week` <= 76)
       AND `fnlwgt` * `education-num` >= `hours-per-week`
       AND `fnlwgt` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Self-emp-not-inc' OR `workclass` = 'Without-pay')
       AND (`education` = '11th')
       AND (`capital-gain` >= 12780 AND `capital-gain` <= 36172)
       AND `fnlwgt` >= `capital-gain`
       AND `age` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 906800 AND `fnlwgt` <= 973999)
       AND (`education` = '12th' OR `education` = '1st-4th')
       AND (`relationship` = 'Unmarried')
       AND (`capital-gain` >= 42628 AND `capital-gain` <= 50520)
       AND `age` + `education-num` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`marital-status` = 'Married-AF-spouse')
       AND (`sex` = 'Female')
       AND (`capital-gain` >= 26860 AND `capital-gain` <= 37471)
       AND `age` + `education-num` >= `capital-gain`
       AND `fnlwgt` + `capital-gain` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Self-emp-inc')
       AND (`marital-status` = 'Married-spouse-absent')
       AND (`occupation` = 'Craft-repair' OR `occupation` = 'Protective-serv')
       AND (`sex` = 'Female')
       AND `age` + `fnlwgt` <= `capital-loss`
       AND `age` + `education-num` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Without-pay')
       AND (   `education` = 'Bachelors'
            OR `education` = 'Some-college'
            OR `education` = 'Assoc-voc'
            OR `education` = '7th-8th'
            OR `education` = 'Preschool')
       AND (`race` = 'White')
       AND (`sex` = 'Male')
       AND `education-num` + `capital-gain` <= `hours-per-week`
       AND `age` + `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 12 AND `education-num` <= 13)
       AND (`race` = 'White')
       AND (`sex` = 'Male')
       AND `education-num` >= `hours-per-week`
       AND `age` * `education-num` <= `capital-gain`
       AND `age` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`sex` = 'Female')
       AND (`capital-gain` >= 89149 AND `capital-gain` <= 91352)
       AND (`capital-loss` >= 2893 AND `capital-loss` <= 2893)
       AND `fnlwgt` * `education-num` >= `capital-loss`
       AND `capital-gain` * `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` = 'Prof-school' OR `education` = 'Assoc-acdm')
       AND (`education-num` >= 5 AND `education-num` <= 9)
       AND (`occupation` = 'Adm-clerical')
       AND `age` * `education-num` <= `hours-per-week`
       AND `education-num` <= `hours-per-week`
       AND `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Private')
       AND (`race` = 'Black')
       AND `fnlwgt` * `capital-loss` <= `hours-per-week`
       AND `education-num` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 22 AND `age` <= 54)
       AND (`race` = 'Asian-Pac-Islander')
       AND (`hours-per-week` >= 88 AND `hours-per-week` <= 99)
       AND `age` + `fnlwgt` <= `education-num`
       AND `fnlwgt` + `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 62 AND `age` <= 79)
       AND (   `marital-status` = 'Never-married'
            OR `marital-status` = 'Separated')
       AND (`occupation` = 'Priv-house-serv')
       AND `education-num` <= `capital-gain`
       AND `education-num` + `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 10 AND `age` <= 30)
       AND (`marital-status` = 'Married-AF-spouse')
       AND (`relationship` = 'Own-child' OR `relationship` = 'Unmarried')
       AND `age` + `education-num` >= `hours-per-week`
       AND `fnlwgt` + `capital-gain` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 1264698 AND `fnlwgt` <= 1360828)
       AND (   `native-country` = 'United-States'
            OR `native-country` = 'England'
            OR `native-country` = 'Cuba'
            OR `native-country` = 'Italy'
            OR `native-country` = 'Peru')
       AND `age` + `capital-gain` >= `capital-loss`
       AND `age` + `education-num` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`occupation` = 'Handlers-cleaners')
       AND (`capital-gain` >= 2122 AND `capital-gain` <= 23907)
       AND `age` >= `hours-per-week`
       AND `age` + `education-num` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`occupation` = 'Other-service' OR `occupation` = 'Prof-specialty')
       AND (`sex` = 'Female')
       AND `age` * `fnlwgt` <= `education-num`
       AND `fnlwgt` + `capital-gain` >= `capital-loss`
       AND `fnlwgt` + `education-num` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` = '11th' OR `education` = 'Prof-school')
       AND (`occupation` = 'Handlers-cleaners')
       AND (`sex` = 'Male')
       AND (`capital-loss` >= 3010 AND `capital-loss` <= 3175)
       AND `fnlwgt` + `education-num` >= `capital-gain`
       AND `education-num` * `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 1225914 AND `fnlwgt` <= 1225914)
       AND (`marital-status` = 'Widowed')
       AND (`capital-loss` >= 2395 AND `capital-loss` <= 3789)
       AND `age` * `fnlwgt` <= `capital-loss`
       AND `age` + `education-num` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 285716 AND `fnlwgt` <= 702275)
       AND (`education` = 'Assoc-voc' OR `education` = 'Masters')
       AND (`education-num` >= 13 AND `education-num` <= 14)
       AND `age` <= `hours-per-week`
       AND `education-num` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`occupation` = 'Craft-repair' OR `occupation` = 'Sales')
       AND (`race` = 'Black')
       AND (   `native-country` = 'Puerto-Rico'
            OR `native-country` = 'Germany'
            OR `native-country` = 'Philippines'
            OR `native-country` = 'Yugoslavia'
            OR `native-country` = 'Trinadad&Tobago')
       AND `age` <= `capital-loss`
       AND `fnlwgt` + `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`relationship` = 'Husband')
       AND (`capital-loss` >= 3258 AND `capital-loss` <= 3757)
       AND (   `native-country` = 'Puerto-Rico'
            OR `native-country` = 'Japan'
            OR `native-country` = 'Cuba'
            OR `native-country` = 'Jamaica')
       AND `education-num` * `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Self-emp-inc')
       AND (`race` = 'Asian-Pac-Islander')
       AND (`native-country` = 'Trinadad&Tobago')
       AND `education-num` * `capital-gain` >= `capital-loss`
       AND `capital-gain` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 985885 AND `fnlwgt` <= 1308924)
       AND (`occupation` = 'Exec-managerial' OR `occupation` = 'Armed-Forces')
       AND (`capital-gain` >= 38556 AND `capital-gain` <= 45823)
       AND `age` - `capital-gain` <= `capital-loss`
       AND `capital-gain` + `capital-loss` >= `hours-per-week`
       AND `age` * `fnlwgt` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 422072 AND `fnlwgt` <= 518261)
       AND (`education-num` >= 6 AND `education-num` <= 6)
       AND (   `occupation` = 'Tech-support'
            OR `occupation` = 'Prof-specialty'
            OR `occupation` = 'Armed-Forces')
       AND (`race` = 'Black')
       AND `fnlwgt` * `capital-gain` >= `capital-loss`
       AND `age` * `education-num` >= `capital-gain`
       AND `fnlwgt` - `capital-gain` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Without-pay')
       AND (`marital-status` = 'Divorced')
       AND (`race` = 'Asian-Pac-Islander')
       AND (`capital-loss` >= 3284 AND `capital-loss` <= 3678)
       AND `education-num` - `capital-loss` <= `hours-per-week`
       AND `education-num` + `capital-gain` >= `hours-per-week`
       AND `capital-gain` + `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Without-pay' OR `workclass` = 'Never-worked')
       AND (`relationship` = 'Wife')
       AND (`sex` = 'Male')
       AND (`native-country` = 'Vietnam')
       AND `fnlwgt` + `education-num` >= `capital-loss`
       AND `fnlwgt` * `capital-gain` <= `capital-loss`
       AND `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Self-emp-not-inc')
       AND (`fnlwgt` >= 331049 AND `fnlwgt` <= 766504)
       AND (`education` = '7th-8th')
       AND `capital-loss` >= `hours-per-week`
       AND `fnlwgt` - `capital-gain` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 15 AND `education-num` <= 16)
       AND (`race` = 'White')
       AND `age` * `fnlwgt` <= `capital-loss`
       AND `education-num` * `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 42 AND `age` <= 42)
       AND (`sex` = 'Male')
       AND (`capital-loss` >= 4289 AND `capital-loss` <= 4465)
       AND `age` + `fnlwgt` <= `capital-loss`
       AND `fnlwgt` <= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 687431 AND `fnlwgt` <= 768134)
       AND (   `occupation` = 'Other-service'
            OR `occupation` = 'Exec-managerial')
       AND (`capital-loss` >= 1663 AND `capital-loss` <= 1818)
       AND `fnlwgt` + `capital-gain` >= `hours-per-week`
       AND `age` + `capital-gain` <= `capital-loss`
       AND `fnlwgt` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 37 AND `age` <= 49)
       AND (`workclass` = 'Without-pay')
       AND `age` * `capital-gain` <= `hours-per-week`
       AND `age` - `fnlwgt` <= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (   `marital-status` = 'Separated'
            OR `marital-status` = 'Married-AF-spouse')
       AND (`sex` = 'Male')
       AND (   `native-country` = 'India'
            OR `native-country` = 'Philippines'
            OR `native-country` = 'Taiwan'
            OR `native-country` = 'Haiti')
       AND `age` + `capital-loss` >= `hours-per-week`
       AND `fnlwgt` * `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 48 AND `age` <= 50)
       AND (`fnlwgt` >= 1133048 AND `fnlwgt` <= 1276817)
       AND (`race` = 'Amer-Indian-Eskimo')
       AND (`sex` = 'Female')
       AND `fnlwgt` + `capital-loss` >= `hours-per-week`
       AND `age` - `fnlwgt` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 8 AND `education-num` <= 10)
       AND (`relationship` = 'Own-child')
       AND `fnlwgt` * `education-num` <= `capital-gain`
       AND `age` + `fnlwgt` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` = '12th' OR `education` = '10th')
       AND (`education-num` >= 10 AND `education-num` <= 10)
       AND (`capital-gain` >= 42294 AND `capital-gain` <= 62782)
       AND `fnlwgt` <= `hours-per-week`
       AND `age` * `fnlwgt` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (   `occupation` = 'Other-service'
            OR `occupation` = 'Protective-serv')
       AND (`capital-loss` >= 3788 AND `capital-loss` <= 4291)
       AND (`native-country` = 'Poland')
       AND `fnlwgt` * `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 9 AND `education-num` <= 12)
       AND (`occupation` = 'Other-service')
       AND (`relationship` = 'Other-relative')
       AND `age` + `education-num` >= `capital-gain`
       AND `age` * `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`race` = 'Black')
       AND (`sex` = 'Male')
       AND (`hours-per-week` >= 3 AND `hours-per-week` <= 17)
       AND `age` <= `education-num`
       AND `age` * `education-num` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Self-emp-inc')
       AND (`education` = 'Assoc-acdm')
       AND (`capital-loss` >= 2786 AND `capital-loss` <= 3087)
       AND `fnlwgt` + `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 1157283 AND `fnlwgt` <= 1228186)
       AND (`capital-gain` >= 46744 AND `capital-gain` <= 58421)
       AND (`capital-loss` >= 2528 AND `capital-loss` <= 2938)
       AND `age` * `capital-gain` >= `hours-per-week`
       AND `fnlwgt` - `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 809538 AND `fnlwgt` <= 809538)
       AND (`marital-status` = 'Divorced')
       AND (`race` = 'White' OR `race` = 'Asian-Pac-Islander')
       AND `fnlwgt` + `education-num` >= `capital-gain`
       AND `fnlwgt` + `education-num` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`marital-status` = 'Divorced')
       AND (`capital-gain` >= 30299 AND `capital-gain` <= 42531)
       AND (`capital-loss` >= 3860 AND `capital-loss` <= 3977)
       AND `fnlwgt` <= `hours-per-week`
       AND `fnlwgt` + `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 1349967 AND `fnlwgt` <= 1429021)
       AND (`education-num` >= 8 AND `education-num` <= 10)
       AND (`hours-per-week` >= 7 AND `hours-per-week` <= 22)
       AND `fnlwgt` <= `hours-per-week`
       AND `education-num` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 3 AND `age` <= 15)
       AND (`education-num` >= 15 AND `education-num` <= 17)
       AND (`marital-status` = 'Married-civ-spouse')
       AND (`capital-gain` >= 55965 AND `capital-gain` <= 60078)
       AND `age` - `capital-gain` <= `capital-loss`
       AND `age` * `capital-gain` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 243198 AND `fnlwgt` <= 422181)
       AND (`marital-status` = 'Married-AF-spouse')
       AND (`race` = 'Black')
       AND (`hours-per-week` >= 64 AND `hours-per-week` <= 78)
       AND `age` * `fnlwgt` <= `education-num`
       AND `age` <= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (`marital-status` = 'Widowed')
       AND (`capital-gain` >= 81518 AND `capital-gain` <= 105243)
       AND `capital-gain` - `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`race` = 'Other')
       AND (`sex` = 'Female')
       AND (`hours-per-week` >= 17 AND `hours-per-week` <= 17)
       AND `fnlwgt` + `education-num` <= `capital-loss`
       AND `education-num` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 1162967 AND `fnlwgt` <= 1413911)
       AND (`education-num` >= 1 AND `education-num` <= 3)
       AND `age` <= `capital-loss`
       AND `capital-gain` + `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Never-worked')
       AND (`education-num` >= 8 AND `education-num` <= 8)
       AND (`sex` = 'Female')
       AND `education-num` <= `capital-gain`
       AND `education-num` * `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 55 AND `age` <= 72)
       AND (`fnlwgt` >= 430243 AND `fnlwgt` <= 567966)
       AND `age` * `capital-loss` <= `hours-per-week`
       AND `age` + `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` = 'Self-emp-not-inc' OR `workclass` = 'State-gov')
       AND (`capital-loss` >= 3301 AND `capital-loss` <= 3499)
       AND (`hours-per-week` >= 32 AND `hours-per-week` <= 53)
       AND (`native-country` = 'Portugal')
       AND `capital-gain` + `capital-loss` >= `hours-per-week`
       AND `age` * `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`relationship` = 'Not-in-family')
       AND (`sex` = 'Male')
       AND (`hours-per-week` >= 6 AND `hours-per-week` <= 6)
       AND (`native-country` = 'England')
       AND `fnlwgt` + `capital-loss` >= `hours-per-week`
       AND `education-num` * `capital-gain` <= `capital-loss`;
