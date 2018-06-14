from __future__ import print_function
from datetime import date, datetime, timedelta
import csv
import nltk
import csv
import re
import sys
import time
from nltk.tag import StanfordPOSTagger
jar = 'C:/Users/fkafou/Documents/stanford-postagger-full-2018-02-27/stanford-postagger-3.9.1.jar'
model = 'C:/Users/fkafou/Documents/stanford-postagger-full-2018-02-27/models/french.tagger'
import os
java_path = "C:/Program Files/Java/jdk1.8.0_171/bin/java.exe"
os.environ['JAVAHOME'] = java_path
pos_tagger = StanfordPOSTagger(model, jar, encoding='utf8')
import mysql.connector

config = {
  'user': 'root',
  'password': '',
  'host': '127.0.0.1',
  'database': 'gottra_tables',
  'raise_on_warnings': True,
}

def add_case(ID, title):
    cnx = mysql.connector.connect(**config)
    cursor = cnx.cursor()
    query = ("INSERT INTO usecase (ID_cas, titre) VALUES (%s, %s)")
    cursor.execute(query, (ID, title))
    cnx.commit()
    cursor.close()
    cnx.close()

def add_actor(acteur):
    cnx = mysql.connector.connect(**config)
    cursor = cnx.cursor()
    query = ("INSERT INTO acteur (nom) VALUES (%s)")
    cursor.execute(query, (acteur,))
    ID_acteur = cursor.lastrowid
    cnx.commit()
    cursor.close()
    cnx.close()
    return ID_acteur

def add_action(action, output, ID_cas, full_text):
    cnx = mysql.connector.connect(**config)
    cursor = cnx.cursor()
    query = ("INSERT INTO action (ID_cas, action, output, full_text) VALUES (%s, %s, %s, %s)")
    cursor.execute(query, (ID_cas, action, output, full_text))
    cnx.commit()
    cursor.close()
    cnx.close()

def add_rule(text, ID_cas):
    cnx = mysql.connector.connect(**config)
    cursor = cnx.cursor()
    query = ("INSERT INTO rgestion (ID_cas, texte) VALUES (%s, %s)")
    cursor.execute(query, (ID_cas, text))
    cnx.commit()
    cursor.close()
    cnx.close()

def actor_exists(acteur):
    cnx = mysql.connector.connect(**config)
    cursor = cnx.cursor()
    query = ("SELECT ID_acteur FROM acteur WHERE nom = %s")
    cursor.execute(query, (acteur,))
    ID = None
    for ID in cursor:
        break;
    cursor.close()
    cnx.close()    
    return ID

def case_exists(ID_cas, titre):
    cnx = mysql.connector.connect(**config)
    cursor = cnx.cursor()
    query = ("SELECT ID_cas FROM usecase WHERE ID_cas = %s AND titre = %s")
    cursor.execute(query, (ID_cas, titre))
    ID = None
    for ID in cursor:
        break;
    cursor.close()
    cnx.close()
    return ID

def update_case(ID_cas, ID_acteur):
    cnx = mysql.connector.connect(**config)
    cursor = cnx.cursor()
    query = ("UPDATE usecase SET ID_acteur = (%s) WHERE ID_cas = (%s)")
    cursor.execute(query, (ID_acteur, ID_cas))
    cnx.commit()
    cursor.close()
    cnx.close()

def get_verb(sentence):
    toks = nltk.word_tokenize(sentence, language = 'french')
    tags = pos_tagger.tag(toks)
    for word in tags:
        if( (word[1] == "V") | (word[1] == "VPP") ):
            return word [0]
    return ''

def get_action_output(sentence):
    split = sentence.split(",")
    if(len(split) > 1):
        action = split[:len(split)-1]
        action = ', '.join(split[:len(split)-1])
        output = str(split[len(split)-1])
    elif(get_verb(sentence)):
        action = sentence[:sentence.find(get_verb(sentence))]
        output = sentence[sentence.find(get_verb(sentence)):]
    else:
        action = sentence
        output = ""
    return (action, output)

def alimenter_bdd(file):
    start_time = time.time()
    regex_cas = re.compile(r'[a-zA-Z]+\d+-\d+')
    with open(file, encoding = "UTF-8") as f:
        action = False
        rgestion = False
        rgestion_text = ''
        for sentence in f:
            sentence = sentence.strip()
            if(action):
                rgestion_text = ''
                if( (sentence == "\n") | (sentence == "") ):
                    action = False
                else:
                    action, output = get_action_output(sentence)
                    add_action(action, output, ID_cas, sentence)
            elif(rgestion):
                if( (sentence == "\n") | (sentence == "") ):
                    rgestion = False
                    add_rule(rgestion_text, ID_cas)
                else:
                    rgestion_text = rgestion_text  + sentence

            else:
                rgestion_text = ''
                if(regex_cas.search(sentence)):
                    position = regex_cas.search(sentence).span()
                    ID_cas = sentence[position[0]:position[1]].strip()
                    title_cas = sentence[position[1]:None].strip()
                    if(case_exists(ID_cas, title_cas) == None):
                        add_case(ID_cas, title_cas)
                elif(sentence.lower().startswith('acteur : ')):
                    sentence = sentence.strip()[9:None]
                    for actor_ in sentence.split('/'):
                        for actor in actor_.split('&'):
                            actor = actor.strip()
                            ID_acteur = actor_exists(actor)
                            if(ID_acteur == None):
                                ID_acteur = add_actor(actor)
                            else:
                                ID_acteur = ID_acteur[0]
                            update_case(ID_cas, ID_acteur)

                elif('actions :' in sentence.lower()):
                    action = True

                elif('règles de gestion :' in sentence.lower()):
                    rgestion = True

alimenter_bdd(str(sys.argv[1]))