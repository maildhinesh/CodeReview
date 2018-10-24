//
//  QuizViewController.swift
//  MathMagic
//
//  Created by d.k.kumararaman on 10/11/18.
//  Copyright © 2018 Kavin Unlimited. All rights reserved.
//

import UIKit
import AVFoundation

class QuizViewController: UIViewController {

    @IBOutlet weak var chooseNumButton: UIButton!
    @IBOutlet weak var exitButton: UIButton!
    
    @IBOutlet var numButtons: [UIButton]!
    
    @IBOutlet weak var incrementLabel: UILabel!
    @IBOutlet weak var tableLabel: UILabel!
    
    @IBOutlet var answerViews: [UIView]!
    @IBOutlet var answerLabels: [UILabel]!
    
    
    //Result Screen
    @IBOutlet weak var resultView: UIView!
    @IBOutlet weak var correctLabel: UILabel!
    @IBOutlet weak var wrongLabel: UILabel!
    @IBOutlet weak var totalTimeLabel: UILabel!
    
    
    let synth = AVSpeechSynthesizer()
    var tableNum : Int = 0
    var quizzes : [Quiz] = []
    var currentQuizIndex : Int = 0
    var currentAnswerDigitIndex : Int = 0
    var currentQuiz = Quiz()
    var startTime = DispatchTime.now()
    var endTime = DispatchTime.now()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.resultView.isHidden = true
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    @IBAction func chooseNumButtonTapped(_ sender: UIButton) {
        numButtons.forEach { (button) in
            UIView.animate(withDuration: 0.3, animations: {
                button.isHidden = !button.isHidden
                self.view.layoutIfNeeded()
            })
        }
    }
    
    enum Numbers: String {
        case one = "One"
        case two = "Two"
        case three = "Three"
        case four = "Four"
        case five = "Five"
        case six = "Six"
        case seven = "Seven"
        case eight = "Eight"
        case nine = "Nine"
        case ten = "Ten"
        case eleven = "Eleven"
        case twelve = "Twelve"
    }
    
    @IBAction func numButtonTapped(_ sender: UIButton) {
        guard let title = sender.currentTitle, let number = Numbers(rawValue: title) else {
            return
        }
        self.tableNum = number.hashValue + 1
        self.exitButton.setTitle("Start", for: .normal)
        chooseNumButton.setTitle(title, for: UIControlState.normal)
        numButtons.forEach { (button) in
            UIView.animate(withDuration: 0.3, animations: {
                button.isHidden = !button.isHidden
                self.view.layoutIfNeeded()
            })
        }
        //startGame()
    }
    
    private func showCurrentQuiz() {
        self.currentQuiz = self.quizzes[currentQuizIndex]
        self.incrementLabel.text = currentQuiz.incrementValue
        self.tableLabel.text = currentQuiz.tableValue
        let utterance = AVSpeechUtterance(string: self.currentQuiz.utteranceString)
        answerViews.forEach { (view) in
            view.isHidden = false
        }
        answerLabels.forEach{ (label) in
            label.text = ""
        }
        if currentQuiz.noOfDigits < 3 {
            answerViews[2].isHidden = true
        }
        if currentQuiz.noOfDigits < 2 {
            answerViews[1].isHidden = true
        }
        currentAnswerDigitIndex = 0
        utterance.rate = AVSpeechUtteranceDefaultSpeechRate
        synth.speak(utterance)
    }
    
    @IBAction func keyTapped(_ sender: UIButton) {
        guard let digit = sender.currentTitle else {
            return
        }
        if digit == currentQuiz.answerDigits[currentAnswerDigitIndex] {
            addDigit(digit, valid: true)
        } else {
            addDigit(digit, valid: false)
        }
    }
    func addDigit(_ digit: String, valid: Bool) {
        self.answerLabels[currentAnswerDigitIndex].text = digit
        if(valid) {
            self.answerLabels[currentAnswerDigitIndex].textColor = UIColor.blue
        } else {
            self.answerLabels[currentAnswerDigitIndex].textColor = UIColor.red
            self.quizzes[self.currentQuizIndex].answered = false
        }
        checkAnswered()
        
    }
    
    func checkAnswered() {
        var enteredAnswer : String = ""
        for i in 0..<currentQuiz.noOfDigits {
            enteredAnswer += answerLabels[i].text!
        }
        
        if enteredAnswer == currentQuiz.answer {
            DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(300) , execute: {
                let utterance = AVSpeechUtterance(string: "Good Job")
                utterance.rate = AVSpeechUtteranceDefaultSpeechRate
                self.synth.speak(utterance)
                self.currentQuizIndex += 1
                if self.currentQuizIndex < 20 {
                    self.showCurrentQuiz()
                } else {
                    self.exitButton.isUserInteractionEnabled = true
                    self.showResult()
                }
            })
        } else {
            self.currentAnswerDigitIndex += 1
        }
    }
    
    func showResult() {
        endTime = DispatchTime.now()
        var correct : Int = 0
        var wrong : Int = 0
        quizzes.forEach{ (quiz) in
            if quiz.answered {
                correct += 1
            } else {
                wrong += 1
            }
        }
        let timeTaken : Double = Double(endTime.uptimeNanoseconds - startTime.uptimeNanoseconds) / 1000000000.0
        self.resultView.isHidden = false
        self.correctLabel.text = "Correct : " + String(correct)
        self.wrongLabel.text = "Wrong : " + String(wrong)
        self.totalTimeLabel.text = "Total Time : " + String(Int(timeTaken.rounded(.toNearestOrAwayFromZero))) + " secs"
        print("Correct : " + String(correct) + " & Wrong: " + String(wrong))
        
        self.incrementLabel.text = ""
        self.tableLabel.text = ""
        answerLabels.forEach{ (label) in
            label.text = ""
        }
        self.chooseNumButton.setTitle("Choose a Table", for: .normal)
    }
    
    @IBAction func backSpaceTapped(_ sender: UIButton) {
        if currentAnswerDigitIndex > 0 {
            currentAnswerDigitIndex -= 1
        }
        self.answerLabels[currentAnswerDigitIndex].text = ""
    }
    
    @IBAction func exitTapped(_ sender: UIButton) {
        guard let title = sender.currentTitle else {
            return
        }
        if title == "Start" {
            startGame()
            sender.setTitle("Exit", for: .normal)
            sender.isUserInteractionEnabled = false
        } else {
            dismiss(animated: true, completion: nil)
        }
    }
    
    private func startGame () {
        for _ in 0..<20 {
            let incrementValue  = arc4random_uniform(12) + 1
            let tableValue = arc4random_uniform(UInt32(tableNum)) + 1
            let answerInt = incrementValue * tableValue
            let quiz : Quiz = Quiz()
            quiz.incrementValue = String(incrementValue)
            quiz.tableValue = String(tableValue)
            quiz.answer = String(answerInt)
            quiz.answerDigits = self.getDigits(number: Int(answerInt))
            quiz.noOfDigits = quiz.answerDigits.count
            quiz.utteranceString = quiz.incrementValue + " times " + quiz.tableValue + "?"
            self.quizzes.append(quiz)
        }
        self.currentQuizIndex = 0
        startTime = DispatchTime.now()
        showCurrentQuiz()
    }
    
    private func getDigits(number: Int) -> [String] {
        let numString = String(number)
        let digits : [String] = numString.compactMap{String($0)}
        return digits
    }
    
    @IBAction func closeButtonTapped(_ sender: UIButton) {
        self.resultView.isHidden = true
    }
    
    
}

class Quiz {
    var incrementValue : String = ""
    var tableValue : String = ""
    var answer: String = ""
    var answerDigits: [String] = []
    var noOfDigits : Int = 0
    var utteranceString : String = ""
    var answered: Bool = true
}
