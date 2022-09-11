'''
Created on Jan 12, 2019

@author: Adrian
'''
from Repository import Repository
from Validator import Validator
from Controller import Controller
from UI import Console


repository = Repository()
validator = Validator()
controller = Controller(validator, repository)
console = Console(controller)

console.run()