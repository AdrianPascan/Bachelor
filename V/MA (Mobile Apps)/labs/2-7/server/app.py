from flask import Flask, jsonify, json, make_response, Response
from werkzeug.serving import run_simple

from controller.ProductController import ProductController
from repository.Repository import Repository


app = Flask(__name__)

repository = Repository()
controller = ProductController(repository)
controller.populate()


@app.route('/')
def index():
    return 'Hello world!'


@app.route('/products')
def get_products():
    return jsonify([product.json for product in controller.get_products()])


@app.route('/products/in_cart')
def get_products_in_cart():
    return jsonify([product.json for product in controller.get_products_in_cart()])


@app.route('/products/add/<int:pid>')
def add_product_to_cart(pid):
    controller.add_product_to_cart(pid)
    return make_response('Product having pid= ' + str(pid) + ' added to cart')


@app.route('/products/delete/<int:pid>')
def delete_product_from_cart(pid):
    controller.delete_product_from_cart(pid)
    return make_response('Product having pid= ' + str(pid) + ' deleted from cart')


@app.route('/products/update/<int:pid>/<int:quantity>')
def update_product_from_cart(pid, quantity):
    controller.update_product_from_cart(pid, quantity)
    return make_response('Product having pid= ' + str(pid) + ' updated in cart: new quantity= ' + str(quantity))


if __name__ == '__main__':
    app.run()