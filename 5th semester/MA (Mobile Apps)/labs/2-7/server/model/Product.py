class Product:
    def __init__(self, pid, brand, gender, type, price, image, in_cart, quantity):
        self.pid = pid
        self.brand = brand
        self.gender = gender
        self.type = type
        self.price = price
        self.image = image
        self.in_cart = in_cart
        self.quantity = quantity

    def __repr__(self):
        return "Product {}=(brand= {}, gender= {}, type= {}, price= {}, image= '{}', in_cart= {}, quantity= {})".format(
            self.pid,
            self.brand,
            self.gender,
            self.type,
            self.price,
            self.image,
            self.in_cart,
            self.quantity,
        )

    def __str__(self):
        return "Product {}: brand= {}, gender= {}, type= {}, price= {}, image= '{}', in_cart= {}, quantity= {}".format(
            self.pid,
            self.brand,
            self.gender,
            self.type,
            self.price,
            self.image,
            self.in_cart,
            self.quantity,
        )

    @property
    def json(self):
        return {
            'pid': self.pid,
            'brand': self.brand,
            'gender': self.gender,
            'type': self.type,
            'price': self.price,
            'image': self.image,
            'in_cart': self.in_cart,
            'quantity': self.quantity,
        }

