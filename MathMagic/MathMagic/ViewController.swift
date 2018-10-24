//
//  ViewController.swift
//  MathMagic
//
//  Created by Kumararaman, D. K. on 2/1/18.
//  Copyright © 2018 Kavin Unlimited. All rights reserved.
//

import UIKit
import AVFoundation

class ViewController: UIViewController {

    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func learnTouched(_ sender: UIButton) {
        self.performSegue(withIdentifier: "showLearnView", sender: nil)
    }
    
    
}

