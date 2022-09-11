from Exceptions.ValidationException import ValidationError
from Domain.StrictlyPositiveIntegerValidator import validateStrictlyPositiveInteger


class ValidatePerson():
    
    def validate(self, person):
        
        errors = ""
        
        try:
            person.ID = validateStrictlyPositiveInteger(person.ID)
        except ValueError as error:
            errors += str(person.ID) + ": person ID " + str(error) + "\n"
        
        if type(person.name) != str or len(person.name.split('_')) != 2:
            errors += "Name must be in this form: LastName_FirstName1-FirstName2-... .\n"
            
        if type(person.phoneNumber) != str or len(person.phoneNumber)!= 10 or person.phoneNumber[0] != '0':
            errors += "Phone number must be in this form: 07xxxxxxxx.\n"
        try:
            int(person.phoneNumber)
        except ValueError:
            errors += "Phone number must contain only digits.\n"
            
        if type(person.address) != str or len(person.address) == 0 :
            errors += "Address must be a nonempty string.\n"
        
        if errors != "":
            raise ValidationError(errors)

