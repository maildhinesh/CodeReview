//
//  UITableRowView.swift
//  MathMagic
//
//  Created by d.k.kumararaman on 10/11/18.
//  Copyright © 2018 Kavin Unlimited. All rights reserved.
//

import UIKit

class UITableRowView: UIView {

    @IBOutlet weak var incrementLabel: UILabel!
    @IBOutlet weak var tableNumLabel: UILabel!
    @IBOutlet weak var valueLabel: UILabel!
    var utteranceString : String = ""
    @IBOutlet var tableRowView: UIView!
    override init(frame: CGRect) {
        super.init(frame: frame)
        commonInit()
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        commonInit()
    }
    
    private func commonInit() {
        Bundle.main.loadNibNamed("TableRowView", owner: self, options: nil)
        addSubview(tableRowView)
        tableRowView.frame = self.bounds
        tableRowView.autoresizingMask = [.flexibleHeight, .flexibleWidth]
        
    }
    
}
