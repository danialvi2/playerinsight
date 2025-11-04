from operator import truediv

from flask import Flask, request, jsonify
from flask_cors import CORS
import random

app = Flask(__name__)
CORS(app) #Lar backend kalle dette fra Localhost

LABELS = ["focused", "frustrated", "idle"]


@app.route("/analyze", methods=["POST"])
def analyze():
    data = request.get_json()

    #Batch: flere tekster i "texts"
    if isinstance(data.get("texts"), list):
        results = []
        for text in data["texts"]:
            label = random.choice(LABELS)
            score = round(random.uniform(0.6, 0.99), 2)
            results.append({
                "input": text,
                "label": label,
                "score": score
            })

        return jsonify(results)

    #Enkelt tekst modus
    text = data.get("text", "")
    label = random.choice(LABELS)
    score = round(random.uniform(0.6, 0.99), 2)
    return jsonify({
        "input": text,
        "label": label,
        "score": score
    })

if __name__== "__main__":
    app.run(host="0.0.0.0", port=2000)
