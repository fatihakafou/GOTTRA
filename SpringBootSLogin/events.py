import nltk
import sys
import os
import re 
import treetaggerwrapper as tt
import csv
import codecs

#nltk.download('wordnet')



def isTitle(sentence):
    p = re.compile('\d+[.]\s*[^\d^\n]+') #\s white space, \d numeric value
    return bool(p.match(sentence))


def isAction(word):
    with open('demonette-1.2.csv', newline='', encoding='utf-8') as f:
        reader = csv.reader(f)
        for row in reader:
            if( (row[0].lower() == word.lower()) & (row[6].startswith("Vmn")) ):
                return True
        return False
    
def isActionTitle(sentence):
    if(isTitle(sentence)):
            action = False
            for word in sentence.split():
                if(isAction(word)):
                    return True
    return False


def process(file):
    #name = os.path.splitext(os.path.basename(file))[0]
    #w = open(name+'_temp.txt', 'w+')
    #w = open(os.path.basename(file), 'w+', 'UTF-8')
    with codecs.open(os.path.basename(file), "w+", "utf-8-sig") as w:

        scope = False
        with open(file) as f:
            for sentence in f:
                #sentence = sentence.encode('utf-8-sig')
                #, encoding='latin-1'
                if(isTitle(sentence)):
                    if(isActionTitle(sentence)):
                        w.write(sentence)
                        scope = True
                    else:
                        scope = False
                else:
                    if(scope):
                        w.write(sentence)
    print("Processing in python is done!")

process(str(sys.argv[1]))
