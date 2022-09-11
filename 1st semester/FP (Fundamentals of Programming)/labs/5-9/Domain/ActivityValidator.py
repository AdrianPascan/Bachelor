from Domain.StrictlyPositiveIntegerValidator import validateStrictlyPositiveInteger
from Exceptions.ValidationException import ValidationError
import datetime


class ValidateActivity():
    
    def validate(self, activity):
        
        errors = ""
        
        try:
            activity.ID = validateStrictlyPositiveInteger(activity.ID)
        except ValueError as error:
            errors += str(activity.ID) + ": activity ID " + str(error) + "\n"
        
        if len(activity.personIDs) == 0:
            errors += "At least one person must participate to the activity.\n"
        else: 
            IDs = activity.personIDs
            for index in range( len(IDs) ):
                try:
                    IDs[index] = validateStrictlyPositiveInteger(IDs[index])
                except ValueError as error:
                    errors += str(IDs[index]) + ": person ID " + error + "\n"
            if len(IDs) != len( set(IDs) ):
                errors += "Persons' IDs must be unique (at least two are not).\n"
        
        try:     
            datetime.datetime.strptime(activity.date, '%d.%m.%Y')
            if len([part  for part in activity.date.split(".")  if len(part) == 1]) != 0 : 
                errors += "If day/month is composed of one digit, it must be written in this form: 0x .\n"
        except ValueError:
            errors += "Date must be in this form: day.month.year .\n"
            
        try:
            datetime.datetime.strptime(activity.time, '%H:%M')
            if len([part  for part in activity.time.split(":")  if len(part) == 1]) != 0 : 
                errors += "If hour/minute is composed of one digit, it must be written in this form: 0x .\n"
        except ValueError:
            errors += "Time must be in this form: hour:minute .\n"
        
        if type(activity.description) != str or len(activity.description) == 0:
            errors += "Description must be a nonempty string.\n"
        
        if errors != "":
            raise ValidationError(errors) 
        
        
        


