
from flask import Flask, render_template, request, redirect, url_for, jsonify
from flask_mail import Mail, Message

app = Flask(__name__)

# Gmail SMTP configuration (replace with your actual email and app password)
app.config['MAIL_SERVER'] = 'smtp.gmail.com'
app.config['MAIL_PORT'] = 587
app.config['MAIL_USERNAME'] = 'your_email@gmail.com'        # your Gmail address
app.config['MAIL_PASSWORD'] = 'your_16_char_app_password'   # app-specific password
app.config['MAIL_USE_TLS'] = True
app.config['MAIL_USE_SSL'] = False

mail = Mail(app)

@app.route('/')
def home():
    return render_template('Home.html')

@app.route('/form', methods=['GET', 'POST'])
def handle_form():
    if request.method == 'POST':
        name = request.form.get('name')
        amount = request.form.get('amount')
        email = request.form.get('email')

        print(f"Received donation from {name} of amount {amount}")

        try:
            msg = Message("Donation Receipt - HopeSA Foundation",
                          sender=app.config['MAIL_USERNAME'],
                          recipients=[email])
            msg.body = f"Dear {name},\n\nThank you for your donation of R{amount}.\n\nHopeSA Foundation appreciates your support!\n\nRegards,\nHopeSA Team"
            mail.send(msg)
        except Exception as e:
            print("Mail failed:", e)

        return jsonify({"message": "Donation received and receipt sent!"})
    return render_template('Form.html')

if __name__ == '__main__':
    app.run(debug=True)
