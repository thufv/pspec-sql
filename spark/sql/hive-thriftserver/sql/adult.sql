SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 1245069 AND `fnlwgt` <= 1245069)
       AND (`race` IN ('Amer-Indian-Eskimo'))
       AND `education-num` >= `capital-loss`
       AND `age` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('Masters'))
       AND (`capital-gain` >= 33581 AND `capital-gain` <= 43022)
       AND (`capital-loss` >= 798 AND `capital-loss` <= 873)
       AND `age` * `fnlwgt` >= `capital-gain`
       AND `education-num` * `capital-gain` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('State-gov'))
       AND (`relationship` IN ('Not-in-family'))
       AND (`hours-per-week` >= 28 AND `hours-per-week` <= 48)
       AND `fnlwgt` + `education-num` >= `capital-gain`
       AND `age` * `fnlwgt` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`marital-status` IN ('Married-spouse-absent'))
       AND (`sex` IN ('Female'))
       AND (`capital-loss` >= 3623 AND `capital-loss` <= 4963)
       AND `age` <= `hours-per-week`
       AND `fnlwgt` * `capital-loss` >= `hours-per-week`
       AND `age` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`relationship` IN ('Husband'))
       AND (`sex` IN ('Male'))
       AND `age` >= `fnlwgt`
       AND `fnlwgt` * `capital-loss` <= `hours-per-week`
       AND `age` * `fnlwgt` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 101244 AND `fnlwgt` <= 569605)
       AND (`marital-status` IN ('Widowed'))
       AND (`relationship` IN ('Own-child'))
       AND (`capital-loss` >= 1970 AND `capital-loss` <= 2883)
       AND `age` >= `education-num`
       AND `education-num` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 70 AND `age` <= 70)
       AND (`workclass` IN ('State-gov'))
       AND (`race` IN ('Amer-Indian-Eskimo'))
       AND `capital-loss` >= `hours-per-week`
       AND `education-num` + `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`race` IN ('Black'))
       AND (`capital-gain` >= 20998 AND `capital-gain` <= 43073)
       AND (`native-country` IN ('Canada'))
       AND `education-num` - `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 3 AND `education-num` <= 7)
       AND (`sex` IN ('Male'))
       AND (`capital-loss` >= 3683 AND `capital-loss` <= 4871)
       AND `education-num` >= `hours-per-week`
       AND `fnlwgt` - `education-num` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 16 AND `education-num` <= 16)
       AND (`occupation` IN ('Farming-fishing', 'Transport-moving'))
       AND (`hours-per-week` >= 50 AND `hours-per-week` <= 50)
       AND `capital-loss` >= `hours-per-week`
       AND `fnlwgt` + `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 68 AND `age` <= 91)
       AND (`workclass` IN ('Self-emp-inc'))
       AND (`race` IN ('Asian-Pac-Islander'))
       AND `age` + `fnlwgt` >= `capital-gain`
       AND `age` * `fnlwgt` <= `hours-per-week`
       AND `education-num` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('7th-8th'))
       AND (`occupation` IN ('Protective-serv'))
       AND (`native-country` IN ('Taiwan', 'El-Salvador'))
       AND `age` - `capital-loss` <= `hours-per-week`
       AND `fnlwgt` + `capital-gain` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 45 AND `age` <= 45)
       AND (`education` IN ('Some-college', '1st-4th', '10th'))
       AND (`hours-per-week` >= 41 AND `hours-per-week` <= 59)
       AND `education-num` * `capital-loss` >= `hours-per-week`
       AND `education-num` + `capital-gain` >= `hours-per-week`
       AND `age` + `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 737297 AND `fnlwgt` <= 898173)
       AND (`marital-status` IN ('Separated'))
       AND (`occupation` IN ('Prof-specialty'))
       AND `age` + `fnlwgt` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 642878 AND `fnlwgt` <= 769335)
       AND (`capital-gain` >= 81270 AND `capital-gain` <= 81270)
       AND `age` >= `fnlwgt`
       AND `fnlwgt` + `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 1101811 AND `fnlwgt` <= 1466525)
       AND (`education` IN ('Some-college', '11th', '9th'))
       AND `fnlwgt` * `education-num` >= `capital-loss`
       AND `fnlwgt` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Private'))
       AND (`education-num` >= 6 AND `education-num` <= 6)
       AND (`sex` IN ('Female'))
       AND `fnlwgt` + `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('Some-college'))
       AND (`race` IN ('White'))
       AND (`hours-per-week` >= 30 AND `hours-per-week` <= 33)
       AND `age` + `fnlwgt` >= `capital-loss`
       AND `age` <= `fnlwgt`;

SELECT AVG(age)
  FROM adult
 WHERE     (`occupation` IN ('Machine-op-inspct'))
       AND (`race` IN ('White'))
       AND (`sex` IN ('Female'))
       AND `fnlwgt` + `education-num` <= `capital-gain`
       AND `education-num` - `capital-gain` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`marital-status` IN ('Married-spouse-absent', 'Married-AF-spouse'))
       AND (`relationship` IN ('Other-relative'))
       AND (`race` IN ('Other'))
       AND (`native-country` IN ('Cambodia', 'Germany', 'Portugal'))
       AND `fnlwgt` + `education-num` >= `capital-gain`
       AND `fnlwgt` <= `hours-per-week`
       AND `fnlwgt` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`marital-status` IN ('Married-AF-spouse'))
       AND (`relationship` IN ('Own-child'))
       AND (`sex` IN ('Female'))
       AND `age` + `education-num` <= `capital-loss`
       AND `education-num` - `capital-gain` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 29 AND `age` <= 35)
       AND (`relationship` IN ('Wife'))
       AND `age` <= `fnlwgt`
       AND `age` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 7 AND `education-num` <= 8)
       AND (`occupation` IN ('Machine-op-inspct'))
       AND (`race` IN ('Amer-Indian-Eskimo'))
       AND (`hours-per-week` >= 39 AND `hours-per-week` <= 45)
       AND `age` >= `hours-per-week`
       AND `fnlwgt` - `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 968274 AND `fnlwgt` <= 1232206)
       AND (`education` IN ('Prof-school', 'Masters', '1st-4th'))
       AND (`relationship` IN ('Other-relative'))
       AND (`hours-per-week` >= 58 AND `hours-per-week` <= 81)
       AND `age` * `fnlwgt` <= `capital-gain`
       AND `age` + `fnlwgt` >= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Self-emp-inc'))
       AND (`capital-loss` >= 1684 AND `capital-loss` <= 1754)
       AND (`hours-per-week` >= 50 AND `hours-per-week` <= 50)
       AND `fnlwgt` + `capital-loss` <= `hours-per-week`
       AND `age` + `fnlwgt` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('Bachelors',
                            'Some-college',
                            '11th',
                            '12th',
                            '1st-4th'))
       AND (`relationship` IN ('Other-relative'))
       AND (`hours-per-week` >= 49 AND `hours-per-week` <= 76)
       AND `age` * `education-num` >= `capital-gain`
       AND `fnlwgt` + `education-num` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 10 AND `age` <= 22)
       AND (`education-num` >= 5 AND `education-num` <= 7)
       AND `fnlwgt` <= `capital-loss`
       AND `education-num` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`relationship` IN ('Other-relative'))
       AND (`race` IN ('Other'))
       AND `age` * `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('State-gov'))
       AND (`fnlwgt` >= 619765 AND `fnlwgt` <= 619765)
       AND (`sex` IN ('Male'))
       AND `education-num` >= `capital-gain`
       AND `fnlwgt` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 83 AND `age` <= 83)
       AND (`sex` IN ('Male'))
       AND (`hours-per-week` >= 10 AND `hours-per-week` <= 10)
       AND `age` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 11 AND `education-num` <= 13)
       AND (`occupation` IN ('Adm-clerical'))
       AND (`capital-gain` >= 15668 AND `capital-gain` <= 39478)
       AND `capital-gain` <= `capital-loss`
       AND `fnlwgt` - `capital-gain` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`marital-status` IN ('Married-civ-spouse'))
       AND (`occupation` IN ('Other-service'))
       AND (`native-country` IN ('Hungary'))
       AND `fnlwgt` * `capital-gain` >= `capital-loss`
       AND `fnlwgt` * `education-num` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('Assoc-voc'))
       AND (`relationship` IN ('Wife'))
       AND `education-num` >= `capital-gain`
       AND `age` + `fnlwgt` >= `capital-gain`
       AND `fnlwgt` + `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`occupation` IN ('Tech-support', 'Prof-specialty'))
       AND (`relationship` IN ('Husband'))
       AND (`capital-loss` >= 1246 AND `capital-loss` <= 1246)
       AND `fnlwgt` - `education-num` <= `capital-loss`
       AND `age` + `fnlwgt` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 29 AND `age` <= 31)
       AND (`capital-loss` >= 2502 AND `capital-loss` <= 3279)
       AND `age` * `capital-gain` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 566176 AND `fnlwgt` <= 597247)
       AND (`marital-status` IN ('Married-spouse-absent'))
       AND (`capital-gain` >= 67758 AND `capital-gain` <= 91083)
       AND `education-num` + `capital-loss` <= `hours-per-week`
       AND `fnlwgt` + `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 50 AND `age` <= 66)
       AND (`workclass` IN ('Without-pay'))
       AND (`native-country` IN ('Italy',
                                 'Mexico',
                                 'Dominican-Republic',
                                 'Columbia'))
       AND `age` * `fnlwgt` >= `education-num`
       AND `education-num` + `capital-gain` >= `hours-per-week`
       AND `age` - `fnlwgt` <= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (`marital-status` IN ('Never-married'))
       AND (`sex` IN ('Female'))
       AND (`hours-per-week` >= 82 AND `hours-per-week` <= 96)
       AND `age` * `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Self-emp-inc', 'Local-gov'))
       AND (`fnlwgt` >= 584784 AND `fnlwgt` <= 584784)
       AND (`sex` IN ('Female'))
       AND (`native-country` IN ('Greece'))
       AND `age` * `capital-gain` <= `capital-loss`
       AND `education-num` - `capital-gain` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('11th'))
       AND (`race` IN ('Other'))
       AND (`native-country` IN ('Outlying-US(Guam-USVI-etc)',
                                 'Philippines',
                                 'Poland',
                                 'Portugal',
                                 'Ireland',
                                 'Hungary'))
       AND `age` * `capital-gain` >= `capital-loss`
       AND `age` - `education-num` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 16 AND `age` <= 33)
       AND (`workclass` IN ('Private'))
       AND (`relationship` IN ('Husband'))
       AND `fnlwgt` - `capital-gain` <= `capital-loss`
       AND `fnlwgt` + `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('10th'))
       AND (`occupation` IN ('Craft-repair', 'Machine-op-inspct'))
       AND (`sex` IN ('Male'))
       AND `education-num` + `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('State-gov'))
       AND (`occupation` IN ('Sales'))
       AND (`relationship` IN ('Own-child'))
       AND (`race` IN ('White'))
       AND `capital-gain` * `capital-loss` <= `hours-per-week`
       AND `education-num` * `capital-gain` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Private'))
       AND (`education` IN ('Assoc-acdm', '1st-4th'))
       AND (`capital-loss` >= 3694 AND `capital-loss` <= 3694)
       AND `age` - `fnlwgt` <= `hours-per-week`
       AND `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('Doctorate'))
       AND (`occupation` IN ('Farming-fishing'))
       AND (`race` IN ('Black'))
       AND (`capital-loss` >= 2351 AND `capital-loss` <= 2600)
       AND `age` * `capital-loss` >= `hours-per-week`
       AND `age` * `education-num` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Federal-gov'))
       AND (`marital-status` IN ('Never-married'))
       AND (`hours-per-week` >= 15 AND `hours-per-week` <= 32)
       AND `fnlwgt` <= `education-num`
       AND `education-num` * `capital-gain` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 82 AND `age` <= 93)
       AND (`workclass` IN ('Private'))
       AND (`occupation` IN ('Machine-op-inspct'))
       AND `age` >= `fnlwgt`
       AND `age` + `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 67 AND `age` <= 72)
       AND (`education-num` >= 12 AND `education-num` <= 14)
       AND (`relationship` IN ('Unmarried'))
       AND `age` * `capital-loss` <= `hours-per-week`
       AND `fnlwgt` + `education-num` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('State-gov'))
       AND (`relationship` IN ('Other-relative'))
       AND (`sex` IN ('Female'))
       AND `age` * `fnlwgt` >= `hours-per-week`
       AND `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 12 AND `education-num` <= 13)
       AND (`sex` IN ('Female'))
       AND (`capital-gain` >= 45489 AND `capital-gain` <= 61672)
       AND `fnlwgt` + `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`sex` IN ('Female'))
       AND (`hours-per-week` >= 13 AND `hours-per-week` <= 23)
       AND `education-num` >= `capital-loss`
       AND `age` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 8 AND `age` <= 8)
       AND (`relationship` IN ('Other-relative'))
       AND (`race` IN ('Amer-Indian-Eskimo'))
       AND `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 1078246 AND `fnlwgt` <= 1404585)
       AND (`relationship` IN ('Wife', 'Own-child'))
       AND (`hours-per-week` >= 38 AND `hours-per-week` <= 47)
       AND (`native-country` IN ('Italy'))
       AND `fnlwgt` <= `capital-loss`
       AND `capital-gain` * `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 61 AND `age` <= 61)
       AND (`education-num` >= 2 AND `education-num` <= 4)
       AND (`capital-loss` >= 4204 AND `capital-loss` <= 4773)
       AND `age` <= `capital-gain`
       AND `age` + `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('Doctorate'))
       AND (`relationship` IN ('Own-child'))
       AND (`sex` IN ('Male'))
       AND `age` * `fnlwgt` <= `capital-loss`
       AND `capital-loss` >= `hours-per-week`
       AND `education-num` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`occupation` IN ('Sales'))
       AND (`capital-gain` >= 60334 AND `capital-gain` <= 86119)
       AND (`capital-loss` >= 3234 AND `capital-loss` <= 3260)
       AND `age` * `capital-gain` >= `hours-per-week`
       AND `fnlwgt` + `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 63 AND `age` <= 63)
       AND (`education-num` >= 14 AND `education-num` <= 14)
       AND `age` <= `capital-gain`
       AND `age` + `education-num` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Self-emp-not-inc'))
       AND (`occupation` IN ('Prof-specialty'))
       AND (`race` IN ('Other'))
       AND `age` <= `education-num`
       AND `education-num` + `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 1339324 AND `fnlwgt` <= 1581502)
       AND (`race` IN ('White'))
       AND (`sex` IN ('Male'))
       AND `fnlwgt` >= `capital-loss`
       AND `education-num` * `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`occupation` IN ('Exec-managerial'))
       AND (`sex` IN ('Male'))
       AND (`native-country` IN ('Greece',
                                 'Cuba',
                                 'Nicaragua',
                                 'Peru'))
       AND `age` * `fnlwgt` <= `capital-loss`
       AND `fnlwgt` * `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 517584 AND `fnlwgt` <= 667889)
       AND (`marital-status` IN ('Never-married'))
       AND (`occupation` IN ('Adm-clerical'))
       AND `education-num` + `capital-gain` >= `capital-loss`
       AND `fnlwgt` + `education-num` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Private', 'State-gov'))
       AND (`sex` IN ('Male'))
       AND (`capital-loss` >= 1782 AND `capital-loss` <= 2191)
       AND (`native-country` IN ('England',
                                 'Outlying-US(Guam-USVI-etc)',
                                 'China',
                                 'Philippines',
                                 'Mexico',
                                 'Nicaragua',
                                 'El-Salvador'))
       AND `age` >= `capital-gain`
       AND `age` + `fnlwgt` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('11th',
                            'Assoc-voc',
                            '1st-4th',
                            '10th'))
       AND (`relationship` IN ('Own-child'))
       AND (`capital-loss` >= 4130 AND `capital-loss` <= 4753)
       AND `age` * `fnlwgt` <= `hours-per-week`
       AND `fnlwgt` - `education-num` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 32 AND `age` <= 44)
       AND (`fnlwgt` >= 1007470 AND `fnlwgt` <= 1360514)
       AND `age` + `education-num` <= `hours-per-week`
       AND `fnlwgt` + `education-num` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`relationship` IN ('Other-relative'))
       AND (`race` IN ('Amer-Indian-Eskimo'))
       AND (`sex` IN ('Male'))
       AND `age` + `fnlwgt` >= `capital-gain`
       AND `education-num` * `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Federal-gov'))
       AND (`marital-status` IN ('Married-civ-spouse'))
       AND (`sex` IN ('Female'))
       AND `age` >= `capital-loss`
       AND `age` + `fnlwgt` >= `capital-gain`
       AND `age` + `fnlwgt` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`marital-status` IN ('Married-civ-spouse'))
       AND (`relationship` IN ('Other-relative'))
       AND (`capital-loss` >= -23 AND `capital-loss` <= 1166)
       AND `fnlwgt` - `education-num` <= `capital-gain`
       AND `age` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 70 AND `age` <= 94)
       AND (`fnlwgt` >= 776195 AND `fnlwgt` <= 814982)
       AND (`marital-status` IN ('Married-spouse-absent', 'Married-AF-spouse'))
       AND `fnlwgt` >= `education-num`
       AND `education-num` + `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 67 AND `age` <= 75)
       AND (`fnlwgt` >= 636324 AND `fnlwgt` <= 725677)
       AND `fnlwgt` + `capital-gain` >= `hours-per-week`
       AND `age` + `fnlwgt` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('State-gov'))
       AND (`fnlwgt` >= 192405 AND `fnlwgt` <= 510879)
       AND (`occupation` IN ('Sales'))
       AND `age` - `fnlwgt` <= `hours-per-week`
       AND `education-num` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`relationship` IN ('Unmarried'))
       AND (`sex` IN ('Male'))
       AND (`capital-loss` >= -328 AND `capital-loss` <= 673)
       AND `age` <= `fnlwgt`
       AND `age` + `education-num` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 864671 AND `fnlwgt` <= 1081205)
       AND (`marital-status` IN ('Married-civ-spouse'))
       AND (`relationship` IN ('Not-in-family'))
       AND (`capital-gain` >= 8271 AND `capital-gain` <= 29830)
       AND `age` * `education-num` <= `capital-gain`
       AND `age` + `fnlwgt` >= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 897558 AND `fnlwgt` <= 926607)
       AND (`occupation` IN ('Priv-house-serv'))
       AND (`hours-per-week` >= 10 AND `hours-per-week` <= 33)
       AND `age` * `fnlwgt` >= `capital-loss`
       AND `age` + `education-num` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`marital-status` IN ('Married-AF-spouse'))
       AND (`relationship` IN ('Wife'))
       AND `age` + `capital-loss` >= `hours-per-week`
       AND `age` + `fnlwgt` >= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('State-gov'))
       AND (`relationship` IN ('Other-relative'))
       AND (`sex` IN ('Female'))
       AND `age` + `fnlwgt` <= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 82 AND `age` <= 90)
       AND (`sex` IN ('Male'))
       AND (`hours-per-week` >= 89 AND `hours-per-week` <= 96)
       AND `fnlwgt` <= `capital-loss`
       AND `age` * `education-num` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Never-worked'))
       AND (`education` IN ('Some-college'))
       AND (`relationship` IN ('Own-child'))
       AND `fnlwgt` * `education-num` <= `capital-loss`
       AND `fnlwgt` + `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 737887 AND `fnlwgt` <= 969127)
       AND (`relationship` IN ('Wife'))
       AND (`hours-per-week` >= 45 AND `hours-per-week` <= 64)
       AND `fnlwgt` * `capital-gain` <= `hours-per-week`
       AND `fnlwgt` >= `education-num`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('Assoc-voc',
                            '9th',
                            '5th-6th',
                            'Preschool'))
       AND (`sex` IN ('Female'))
       AND (`capital-gain` >= 59084 AND `capital-gain` <= 80646)
       AND `fnlwgt` >= `capital-gain`
       AND `fnlwgt` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 11 AND `age` <= 15)
       AND (`education` IN ('9th', '1st-4th'))
       AND `fnlwgt` + `education-num` <= `capital-gain`
       AND `education-num` * `capital-loss` >= `hours-per-week`
       AND `capital-gain` + `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Federal-gov'))
       AND (`education` IN ('Assoc-acdm', '1st-4th'))
       AND (`capital-loss` >= 1005 AND `capital-loss` <= 1513)
       AND `age` * `fnlwgt` <= `capital-loss`
       AND `fnlwgt` + `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 3 AND `education-num` <= 3)
       AND (`marital-status` IN ('Never-married'))
       AND (`hours-per-week` >= 96 AND `hours-per-week` <= 96)
       AND `age` + `capital-gain` <= `capital-loss`
       AND `fnlwgt` * `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('State-gov'))
       AND (`fnlwgt` >= 650858 AND `fnlwgt` <= 783731)
       AND (`race` IN ('White'))
       AND `fnlwgt` >= `hours-per-week`
       AND `age` * `fnlwgt` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`relationship` IN ('Husband'))
       AND (`capital-gain` >= 11618 AND `capital-gain` <= 16948)
       AND (`native-country` IN ('Poland', 'Jamaica', 'Portugal'))
       AND `capital-gain` <= `hours-per-week`
       AND `age` * `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('HS-grad', '7th-8th'))
       AND (`marital-status` IN ('Married-AF-spouse'))
       AND (`capital-loss` >= 2340 AND `capital-loss` <= 2958)
       AND `fnlwgt` <= `hours-per-week`
       AND `fnlwgt` - `education-num` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`occupation` IN ('Transport-moving'))
       AND (`capital-loss` >= 3553 AND `capital-loss` <= 4426)
       AND `age` <= `education-num`
       AND `fnlwgt` + `education-num` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 480611 AND `fnlwgt` <= 480611)
       AND (`hours-per-week` >= 71 AND `hours-per-week` <= 97)
       AND `age` + `capital-loss` >= `hours-per-week`
       AND `fnlwgt` * `capital-gain` <= `capital-loss`
       AND `age` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('11th'))
       AND (`education-num` >= 9 AND `education-num` <= 10)
       AND (`marital-status` IN ('Married-AF-spouse'))
       AND `age` >= `hours-per-week`
       AND `fnlwgt` + `education-num` >= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Self-emp-not-inc'))
       AND (`capital-gain` >= 12291 AND `capital-gain` <= 12291)
       AND `fnlwgt` * `education-num` >= `capital-gain`
       AND `age` + `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Federal-gov'))
       AND (`education` IN ('Masters'))
       AND (`education-num` >= 6 AND `education-num` <= 8)
       AND `age` + `fnlwgt` >= `education-num`
       AND `fnlwgt` + `capital-gain` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education-num` >= 8 AND `education-num` <= 8)
       AND (`marital-status` IN ('Separated'))
       AND (`race` IN ('Asian-Pac-Islander'))
       AND `age` * `capital-loss` >= `hours-per-week`
       AND `age` + `education-num` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 1089543 AND `fnlwgt` <= 1270151)
       AND (`relationship` IN ('Own-child'))
       AND (`capital-loss` >= 1391 AND `capital-loss` <= 1692)
       AND (`native-country` IN ('Iran',
                                 'Columbia',
                                 'Nicaragua',
                                 'Holand-Netherlands'))
       AND `fnlwgt` >= `education-num`
       AND `fnlwgt` + `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`fnlwgt` >= 281089 AND `fnlwgt` <= 589966)
       AND (`education-num` >= 9 AND `education-num` <= 11)
       AND (`relationship` IN ('Husband'))
       AND `age` + `education-num` >= `capital-gain`
       AND `age` + `capital-gain` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`sex` IN ('Female'))
       AND (`hours-per-week` >= -6 AND `hours-per-week` <= 14)
       AND `fnlwgt` + `education-num` >= `capital-gain`
       AND `fnlwgt` + `capital-loss` >= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('Assoc-acdm'))
       AND (`education-num` >= 3 AND `education-num` <= 7)
       AND (`relationship` IN ('Own-child'))
       AND `education-num` <= `capital-gain`
       AND `age` + `capital-gain` <= `hours-per-week`
       AND `age` - `fnlwgt` <= `capital-gain`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Self-emp-inc', 'Federal-gov'))
       AND (`education-num` >= 12 AND `education-num` <= 13)
       AND (`sex` IN ('Female'))
       AND (`capital-loss` >= 573 AND `capital-loss` <= 573)
       AND `capital-gain` + `capital-loss` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 54 AND `age` <= 69)
       AND (`workclass` IN ('State-gov'))
       AND (`fnlwgt` >= 155503 AND `fnlwgt` <= 371208)
       AND (`relationship` IN ('Own-child'))
       AND `age` - `capital-loss` <= `hours-per-week`
       AND `age` + `capital-gain` <= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`workclass` IN ('Never-worked'))
       AND (`hours-per-week` >= 49 AND `hours-per-week` <= 60)
       AND `education-num` * `capital-gain` <= `hours-per-week`
       AND `age` * `fnlwgt` >= `capital-loss`;

SELECT AVG(age)
  FROM adult
 WHERE     (`age` >= 34 AND `age` <= 39)
       AND (`education` IN ('Prof-school',
                            'Assoc-acdm',
                            'Masters',
                            '10th'))
       AND (`race` IN ('Black'))
       AND `education-num` + `capital-gain` >= `hours-per-week`
       AND `age` <= `hours-per-week`;

SELECT AVG(age)
  FROM adult
 WHERE     (`education` IN ('10th'))
       AND (`education-num` >= 8 AND `education-num` <= 11)
       AND (`native-country` IN ('Japan', 'Honduras'))
       AND `education-num` <= `capital-loss`
       AND `age` + `fnlwgt` >= `capital-loss`;
