from model.Product import Product


class ProductController:
    def __init__(self, repository):
        self.repository = repository

    def get_products(self):
        return self.repository.data
    
    def get_products_in_cart(self):
        return [product for product in self.repository.data if product.in_cart == 1]

    def add_product_to_cart(self, pid):
        product = next(prod for prod in self.repository.data if prod.pid == pid)
        product.in_cart = 1
        product.quantity = 1

    def delete_product_from_cart(self, pid):
        product = next(prod for prod in self.repository.data if prod.pid == pid)
        product.in_cart = 0
        product.quantity = 0

    def update_product_from_cart(self, pid, quantity):
        product = next(prod for prod in self.repository.data if prod.pid == pid)
        product.quantity = quantity

    def populate(self):
        product = Product(
            pid=1,
            brand='Dr. Martens',
            gender='unisex',
            type='boots',
            price=189,
            image='dr_martens.jpg',
            in_cart=0,
            quantity=0
        )
        self.repository.data.append(product)

        product = Product(
            pid=2,
            brand='Christian Louboutin',
            gender='women\'s',
            type='high heels',
            price=699,
            image='louboutin.png',
            in_cart=0,
            quantity=0
        )
        self.repository.data.append(product)

        product = Product(
            pid=3,
            brand='Valentino Garavani',
            gender='women\'s',
            type='high heels',
            price=749,
            image='valentino.jpg',
            in_cart=0,
            quantity=0
        )
        self.repository.data.append(product)
