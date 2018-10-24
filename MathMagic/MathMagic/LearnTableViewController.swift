//
//  LearnTableViewController.swift
//  MathMagic
//
//  Created by d.k.kumararaman on 10/11/18.
//  Copyright © 2018 Kavin Unlimited. All rights reserved.
//

import UIKit
import AVFoundation

class LearnTableViewController: UIViewController {

    @IBOutlet weak var menuButton: UIButton!
    
    @IBOutlet var tableButtons: [UIButton]!
    
    @IBOutlet weak var prevBarButton: UIButton!
    @IBOutlet weak var nextBarButton: UIButton!
    @IBOutlet var tableRows: [UITableRowView]!
    var currentIndex = 0
    let synth = AVSpeechSynthesizer()
    var utterance = AVSpeechUtterance(string: "")
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func menuButtonTapped(_ sender: UIButton) {
        tableButtons.forEach { (button) in
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
    
    @IBAction func tableButtonTapped(_ sender: UIButton) {
        
        guard let title = sender.currentTitle, let number = Numbers(rawValue: title) else {
            return
        }
        menuButton.setTitle(title, for: UIControlState.normal)
        tableButtons.forEach { (button) in
            UIView.animate(withDuration: 0.3, animations: {
                button.isHidden = !button.isHidden
                self.view.layoutIfNeeded()
            })
        }
        hideAll(true)
        prepareTable(number.hashValue + 1)
    }
    
    private func hideAll(_ hide: Bool) {
        tableRows.forEach { (tableRow) in
            tableRow.isHidden = hide
        }
        self.prevBarButton.isEnabled = false
    }
    
    private func prepareTable(_ table: Int) {
        for i in 1..<13 {
            let tableRow = tableRows[i-1]
            tableRow.incrementLabel.text = String(i)
            tableRow.tableNumLabel.text = String(table)
            let sum = i * table
            tableRow.valueLabel.text = String(sum)
            tableRow.utteranceString = String(i) + " times " + String(table) + " is " + String(sum)
            
        }
        self.currentIndex = -1
    }
    private func showTableRow(_ tableRow : UITableRowView) {
        UIView.animate(withDuration: 0.3, animations: {
            tableRow.isHidden = !tableRow.isHidden
            self.view.layoutIfNeeded()
        })
    }
    
    private func speak(_ utteranceString : String) {
        self.utterance = AVSpeechUtterance(string: utteranceString)
        self.utterance.rate = AVSpeechUtteranceDefaultSpeechRate
        synth.speak(self.utterance)
    }
    
    @IBAction func showPrevRow(_ sender: UIButton) {
        if self.currentIndex > 0 {
            showTableRow(tableRows[self.currentIndex])
            self.currentIndex = self.currentIndex - 1
            let tableRow = tableRows[self.currentIndex]
            speak(tableRow.utteranceString)
        }
        if !nextBarButton.isEnabled {
            nextBarButton.isEnabled = true
        }
    }
    @IBAction func speakAgain(_ sender: UIButton) {
        speak(tableRows[self.currentIndex].utteranceString)
    }
    @IBAction func stopAndExit(_ sender: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func showNextRow(_ sender: UIButton) {
        if self.currentIndex < 12 {
            self.currentIndex = self.currentIndex + 1
            let tableRow = self.tableRows[self.currentIndex]
            showTableRow(tableRow)
            speak(tableRow.utteranceString)
        }
        if self.currentIndex == 11 {
            self.nextBarButton.isEnabled = false
        }
        if !self.prevBarButton.isEnabled {
            self.prevBarButton.isEnabled = true
        }
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
